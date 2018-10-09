package ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.repository.weather.common.AbsCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.utils.LocationUnknown
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import javax.inject.Inject

class OWMCurrentWeatherRepository
@Inject constructor(
    private val apiService: IOWMCurrentWeatherApiService,
    networkManager: NetworkManager,
    currentWeatherDbService: ICurrentWeatherDbService<OWMCurrentWeatherResponseType>,
    locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context
) :
    AbsCurrentWeatherRepository<OWMCurrentWeatherResponseType>(
        currentWeatherDbService, locationDbService, context, networkManager
    ),
    ICurrentWeatherRepository<OWMCurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<OWMCurrentWeatherResponseType> =
        getCurrentWeatherHelper()

    override fun makeApiCall(): Single<OWMCurrentWeatherResponseType> {
        val currentAddress: UserAddressType? = getCurrentAddress()

        return if (currentAddress != null) {
            val cityName = currentAddress.locality

            val coords = currentAddress.coords

            val postalCode = currentAddress.postalCode

            when {
                cityName != null -> getCurrentWeatherByCityName(cityName)

                coords.areNotNull() -> getCurrentWeatherByGeographicCoordinates(coords!!)

                postalCode != null -> getCurrentWeatherByZipCode(
                    postalCode,
                    currentAddress.countryCode
                )

                else -> {
                    val error =
                        LocationUnknown(context.getString(R.string.current_location_unknown))

                    Single.error(error)
                }
            }
        } else {
            val error =
                LocationUnknown(context.getString(R.string.current_location_unknown))

            Single.error(error)
        }
    }

    private fun getCurrentWeatherByZipCode(
        zipCode: Int,
        countryCode: String?
    ): Single<OWMCurrentWeatherResponseType> {
        val zip = if (countryCode != null) "$zipCode,$countryCode" else "$zipCode"

        return apiService.getCurrentWeatherByZipCode(zip)
    }

    private fun getCurrentWeatherByGeographicCoordinates(
        coords: GeographicCoordinates
    ): Single<OWMCurrentWeatherResponseType> =
        apiService.getCurrentWeatherByGeographicCoordinates(coords.latitude!!, coords.longitude!!)

    private fun getCurrentWeatherByCityName(cityName: String): Single<OWMCurrentWeatherResponseType> =
        apiService.getCurrentWeatherByCityName(cityName)

}
