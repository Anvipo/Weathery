package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.onDbHasOutdatedWeatherResponse
import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherRealmResponse
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.ICoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import kotlin.math.abs

abstract class AbsCurrentWeatherRepository<T : ICurrentWeatherRealmResponse>(
    private val currentWeatherDbService: ICurrentWeatherDbService<T>,
    locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context,
    private val networkManager: NetworkManager
) : AbsWeatherRepository(locationDbService, context, networkManager) {

    protected abstract fun makeApiCall(): Single<T>

    protected fun getCurrentWeatherHelper(): Single<T> =
        if (networkManager.isConnectedToInternet) {
            makeDBCall().onErrorResumeNext(::onDbCallError)
        } else {
            makeDBCall()
        }

    private fun makeDBCall(): Single<T> =
        currentWeatherDbService
            .getCurrentWeatherResponse(networkManager.isConnectedToInternet)
            .flatMap(::locationChangeCheck)

    @Suppress("UNUSED_PARAMETER")
    private fun onDbCallError(error: Throwable): Single<T> = makeApiCall().flatMap(::saveInDB)

    private fun saveInDB(currentWeatherResponse: T): Single<T> =
        currentWeatherDbService.saveCurrentWeatherResponse(currentWeatherResponse)

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