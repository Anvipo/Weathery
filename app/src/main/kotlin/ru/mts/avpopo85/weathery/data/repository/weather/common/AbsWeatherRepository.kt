package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.weather.utils.PreviousLocationUnknownException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

abstract class AbsWeatherRepository(
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

}