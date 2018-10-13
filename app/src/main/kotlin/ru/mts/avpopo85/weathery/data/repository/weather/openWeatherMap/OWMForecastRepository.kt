package ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMForecastResponse
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMForecastApiService
import ru.mts.avpopo85.weathery.data.repository.weather.common.AbsForecastRepository
import ru.mts.avpopo85.weathery.data.repository.weather.utils.onUnknownCurrentLocation
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastResponseType
import javax.inject.Inject


class OWMForecastRepository
@Inject constructor(
    private val apiService: IOWMForecastApiService,
    networkManager: NetworkManager,
    forecastDbService: IForecastDbService<OWMForecastResponseType>,
    locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context
) :
    AbsForecastRepository<OWMForecastResponseType>(
        networkManager,
        forecastDbService,
        locationDbService,
        context
    ),
    IForecastRepository<OWMForecastListResponseType> {

    override fun getForecast(): Single<OWMForecastListResponseType> = getForecastHelper()

    override fun makeApiCall(): Single<OWMForecastListResponseType> {
        val lastKnownAddress = getLastKnownAddress()

        return if (lastKnownAddress != null) {
            val cityName: String? = lastKnownAddress.locality

            val coords: GeographicCoordinates? = lastKnownAddress.coords

            val postalCode: Int? = lastKnownAddress.postalCode

            val apiCall: Single<OWMForecastResponse> = when {
                cityName != null -> getCurrentWeatherByCityName(cityName)

                coords.areNotNull() -> getCurrentWeatherByGeographicCoordinates(coords!!)

                postalCode != null -> getCurrentWeatherByZipCode(
                    postalCode,
                    lastKnownAddress.countryCode
                )

                else -> context.onUnknownCurrentLocation()
            }

            apiCall.map { it.forecastsList }
        } else {
            context.onUnknownCurrentLocation()
        }
    }

    private fun getCurrentWeatherByZipCode(
        zipCode: Int,
        countryCode: String?
    ): Single<OWMForecastResponse> {
        val zip = if (countryCode != null) "$zipCode,$countryCode" else "$zipCode"

        return apiService.getCurrentWeatherByZipCode(zip)
    }

    private fun getCurrentWeatherByGeographicCoordinates(
        coords: GeographicCoordinates
    ): Single<OWMForecastResponse> =
        apiService.getCurrentWeatherByGeographicCoordinates(coords.latitude!!, coords.longitude!!)

    private fun getCurrentWeatherByCityName(cityName: String): Single<OWMForecastResponse> =
        apiService.getCurrentWeatherByCityName(cityName)

}
