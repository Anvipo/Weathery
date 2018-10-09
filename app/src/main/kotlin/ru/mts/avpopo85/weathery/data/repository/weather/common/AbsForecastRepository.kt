package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastResponse
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.utils.common.MyRealmException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

abstract class AbsForecastRepository<T : IForecastResponse>(
    private val networkManager: NetworkManager,
    private val forecastDbService: IForecastDbService<T>,
    private val locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context
) {

    protected abstract fun makeApiCall(): Single<List<T>>

    protected fun getForecastHelper(): Single<List<T>> =
        if (!networkManager.isConnectedToInternet) {
            makeDBCall()
        } else {
            makeDBCall().onErrorResumeNext(::onDbCallError)
        }

    protected fun getLastKnownAddress(): UserAddressType? = try {
        locationDbService.getLastKnownAddress(
            isGpsProviderEnabled = networkManager.isGpsProviderEnabled,
            isNetworkProviderEnabled = networkManager.isNetworkProviderEnabled,
            isConnectedToInternet = networkManager.isConnectedToInternet
        ).blockingGet()
    } catch (exception: Exception) {
        throw Throwable("${context.getString(R.string.db_has_no_location_data)}\n${exception.localizedMessage}")
    }

    protected fun GeographicCoordinates?.areNotNull(): Boolean =
        this != null && this.latitudeAndLongitudeAreNotNull()

    private fun makeDBCall(): Single<List<T>> =
        forecastDbService.getForecastResponse(networkManager.isConnectedToInternet)

    private fun onDbCallError(error: Throwable): Single<List<T>> =
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

    private fun saveInDB(currentWeatherResponse: List<T>): Single<List<T>> =
        forecastDbService.saveForecastResponse(currentWeatherResponse)

}