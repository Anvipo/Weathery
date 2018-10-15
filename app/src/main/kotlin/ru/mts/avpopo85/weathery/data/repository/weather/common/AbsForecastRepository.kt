package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.onDbHasOutdatedWeatherResponse
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.ICoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import kotlin.math.abs

abstract class AbsForecastRepository<T : IForecastRealmResponse>(
    private val networkManager: NetworkManager,
    private val forecastDbService: IForecastDbService<T>,
    locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context
) : AbsWeatherRepository(locationDbService, context, networkManager) {

    protected abstract fun makeApiCall(): Single<List<T>>

    protected fun getForecastHelper(): Single<List<T>> =
        if (!networkManager.isConnectedToInternet) {
            makeDBCall()
        } else {
            makeDBCall().onErrorResumeNext(::onDbCallError)
        }

    private fun makeDBCall(): Single<List<T>> =
        forecastDbService
            .getForecastResponse(networkManager.isConnectedToInternet)
            .flatMap {
                locationChangeCheck(it.first())

                Single.just(it)
            }

    @Suppress("UNUSED_PARAMETER")
    private fun onDbCallError(error: Throwable): Single<List<T>> =
        makeApiCall().flatMap(::saveInDB)

    private fun saveInDB(currentWeatherResponse: List<T>): Single<List<T>> =
        forecastDbService.saveForecastResponse(currentWeatherResponse)

    private fun locationChangeCheck(weatherData: T): Single<T> = Single.create { emitter ->
        val previousCoords = weatherData.coordinates

        val currentCoords = getLastKnownAddress().coords

        if (coordsAreNotNull(previousCoords, currentCoords)) {
            val latitudeDiff = abs(previousCoords!!.latitude!! - currentCoords!!.latitude!!)
            val longitudeDiff = abs(previousCoords.longitude!! - currentCoords.longitude!!)

            if (latitudeDiff > 0.5 || longitudeDiff > 0.5) {
                context.onDbHasOutdatedWeatherResponse(
                    emitter,
                    networkManager.isConnectedToInternet,
                    true
                )
            } else {
                emitter.onSuccess(weatherData)
            }
        } else {
            emitter.onSuccess(weatherData)
        }
    }

    private fun coordsAreNotNull(
        previousCoords: ICoordinates?,
        currentCoords: GeographicCoordinates?
    ): Boolean = previousCoords != null && currentCoords != null &&
            previousCoords.latitude != null && previousCoords.longitude != null &&
            currentCoords.latitude != null && currentCoords.longitude != null

}