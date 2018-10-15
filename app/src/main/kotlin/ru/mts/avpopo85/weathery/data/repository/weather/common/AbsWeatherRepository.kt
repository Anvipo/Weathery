package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.onDbHasOutdatedWeatherResponse
import ru.mts.avpopo85.weathery.data.model.base.common.IWeatherResponse
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.ICoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.weather.utils.PreviousLocationUnknownException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import kotlin.math.abs

abstract class AbsWeatherRepository<T : IWeatherResponse>(
    private val locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context,
    private val networkManager: NetworkManager
) {

    protected fun getLastKnownAddress(): UserAddressType =
        try {
            locationDbService.getLastKnownAddress(
                isGpsProviderEnabled = networkManager.isGpsProviderEnabled,
                isNetworkProviderEnabled = networkManager.isNetworkProviderEnabled,
                isConnectedToInternet = networkManager.isConnectedToInternet
            ).blockingGet()
        } catch (exception: Exception) {
            val part1 = context.getString(R.string.previous_location_unknown)
            val part2 = exception.localizedMessage ?: exception.message

            val message: String = if (part2 != null) "$part1\n$part2" else part1

            throw PreviousLocationUnknownException(message)
        }

    protected fun GeographicCoordinates?.areNotNull(): Boolean =
        this != null && this.latitudeAndLongitudeAreNotNull()

    protected fun locationChangeCheck(weatherData: T): Single<T> = Single.create { emitter ->
        val previousCoords = weatherData.coordinates

        val currentCoords = getLastKnownAddress().coords

        if (coordsAreNotNull(previousCoords, currentCoords)) {
            val latitudeDiff = abs(previousCoords!!.latitude!! - currentCoords!!.latitude!!)
            val longitudeDiff = abs(previousCoords.longitude!! - currentCoords.longitude!!)

            val locationHasChanged = latitudeDiff > 0.1 || longitudeDiff > 0.1

            if (locationHasChanged) {
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
