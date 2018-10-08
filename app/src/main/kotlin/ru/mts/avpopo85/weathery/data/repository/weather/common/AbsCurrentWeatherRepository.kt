package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherRealmResponse
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.utils.common.MyRealmException

abstract class AbsCurrentWeatherRepository<T : ICurrentWeatherRealmResponse>(
    private val currentWeatherDbService: ICurrentWeatherDbService<T>,
    private val locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context,
    private val networkManager: NetworkManager
) {

    protected abstract fun makeApiCall(): Single<T>

    protected fun getCurrentWeatherHelper(): Single<T> =
        if (!networkManager.isConnectedToInternet) {
            makeDBCall()
        } else {
            makeDBCall().onErrorResumeNext(::onDbCallError)
        }

    protected fun getCurrentAddress(): UserAddressType? = try {
        locationDbService.getAddress().blockingGet()
    } catch (exception: Exception) {
        throw Throwable("${context.getString(R.string.db_has_no_location_data)}\n${exception.localizedMessage}")
    }

    protected fun GeographicCoordinates?.areNotNull(): Boolean =
        this != null && this.latitudeAndLongitudeAreNotNull()

    private fun makeDBCall(): Single<T> =
        currentWeatherDbService.getCurrentWeatherResponse(networkManager.isConnectedToInternet)

    private fun onDbCallError(error: Throwable): Single<T> =
        if (error is MyRealmException.DBHasNothing ||
            error is MyRealmException.DBHasOutdatedData
        ) {
            makeApiCall().flatMap(::saveInDB)
        } else {
            if (BuildConfig.DEBUG) {
                error.printStackTrace()

                context.longToast(error.localizedMessage ?: error.message ?: "")
            }

            makeApiCall().flatMap(::saveInDB)
        }

    private fun saveInDB(currentWeatherResponse: T): Single<T> =
        currentWeatherDbService.saveCurrentWeatherResponse(currentWeatherResponse)

}