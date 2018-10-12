package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherRealmResponse
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

abstract class AbsCurrentWeatherRepository<T : ICurrentWeatherRealmResponse>(
    private val currentWeatherDbService: ICurrentWeatherDbService<T>,
    private val locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context,
    private val networkManager: NetworkManager
) {

    protected abstract fun makeApiCall(): Single<T>

    protected fun getCurrentWeatherHelper(): Single<T> =
        if (networkManager.isConnectedToInternet) {
            makeDBCall().onErrorResumeNext(::onDbCallError)
        } else {
            makeDBCall()
        }

    protected fun getLastKnownAddress(): UserAddressType = try {
        locationDbService.getLastKnownAddress(
            isGpsProviderEnabled = networkManager.isGpsProviderEnabled,
            isNetworkProviderEnabled = networkManager.isNetworkProviderEnabled,
            isConnectedToInternet = networkManager.isConnectedToInternet
        ).blockingGet()
    } catch (exception: Exception) {
        val part1 = context.getString(R.string.db_has_no_location_data)
        val part2 = exception.localizedMessage ?: exception.message

        val message = "$part1\n$part2"

        throw Throwable(message)
    }

    protected fun GeographicCoordinates?.areNotNull(): Boolean =
        this != null && this.latitudeAndLongitudeAreNotNull()

    private fun makeDBCall(): Single<T> =
        currentWeatherDbService.getCurrentWeatherResponse(networkManager.isConnectedToInternet)

    @Suppress("UNUSED_PARAMETER")
    private fun onDbCallError(error: Throwable): Single<T> =
        makeApiCall().flatMap(::saveInDB)

    private fun saveInDB(currentWeatherResponse: T): Single<T> =
        currentWeatherDbService.saveCurrentWeatherResponse(currentWeatherResponse)

}