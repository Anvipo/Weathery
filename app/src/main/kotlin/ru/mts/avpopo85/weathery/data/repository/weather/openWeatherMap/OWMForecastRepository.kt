package ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMForecastApiService
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.weather.common.AbsForecastRepository
import ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.base.IOWMRepository
import ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.utils.TripleOfOWMApiCalls
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMListItemResponseType
import javax.inject.Inject


class OWMForecastRepository
@Inject constructor(
    private val apiService: IOWMForecastApiService,
    networkManager: NetworkManager,
    forecastDbService: IForecastDbService<OWMListItemResponseType>,
    locationDbService: ILocationDbService,
    context: Context
) :
    AbsForecastRepository<OWMListItemResponseType>(
        forecastDbService, networkManager, locationDbService, context
    ),
    IForecastRepository<OWMForecastListResponseType>,
    IOWMRepository<OWMForecastResponseType> {

    override fun onNewLocation(): Single<OWMForecastListResponseType> = onNewLocationHelper()

    override fun getForecast(): Single<OWMForecastListResponseType> = getForecastHelper()

    override fun makeApiCall(): Single<OWMForecastListResponseType> {
        val (
                firstApiCall,
                secondApiCall,
                thirdApiCall
        ) = make3ApiCalls()

        return makeApiCall(firstApiCall, secondApiCall, thirdApiCall)
            .map { forecastResponse ->
                forecastResponse.forecastsList.map {
                    it.apply {
                        cityName = forecastResponse.city.name

                        val currentCoords = forecastResponse.city.coordinates

                        val coords = GeographicCoordinates(
                            latitude = currentCoords.latitude,
                            longitude = currentCoords.longitude
                        )

                        coordinates = coords
                    }
                }
            }
    }

    override fun make3ApiCalls(): TripleOfOWMApiCalls<OWMForecastResponseType> {
        val currentAddress: UserAddressType = getLastKnownAddress()

        val coords: GeographicCoordinates? = currentAddress.coords
        val cityName: String? = currentAddress.locality
        val countryCode: String? = currentAddress.countryCode
        val postalCode: String? = currentAddress.postalCode

        val geoCoordsApiCall = makeGeoCoordsApiCall(coords)
        val postalCodeApiCall = makePostalCodeApiCall(postalCode, countryCode)
        val cityNameApiCall = makeCityNameApiCall(cityName, countryCode)

        return TripleOfOWMApiCalls(geoCoordsApiCall, postalCodeApiCall, cityNameApiCall)
    }

    override fun makePostalCodeApiCall(
        postalCode: String?,
        countryCode: String?
    ): Single<OWMForecastResponseType>? =
        if (postalCode != null) {
            getWeatherDataByZipCode(postalCode, countryCode)
        } else {
            null
        }

    override fun makeCityNameApiCall(
        cityName: String?,
        countryCode: String?
    ): Single<OWMForecastResponseType>? =
        if (cityName != null) {
            getWeatherDataByCityName(cityName, countryCode)
        } else {
            null
        }

    override fun makeGeoCoordsApiCall(coords: GeographicCoordinates?): Single<OWMForecastResponseType>? =
        if (coords != null) {
            getWeatherDataByGeographicCoordinates(coords)
        } else {
            null
        }

    override fun getWeatherDataByZipCode(
        postalCode: String,
        countryCode: String?
    ): Single<OWMForecastResponseType> {
        val zip = if (countryCode != null) "$postalCode,$countryCode" else postalCode

        return apiService.getForecastByZipCode(zip)
    }

    override fun getWeatherDataByGeographicCoordinates(
        coords: GeographicCoordinates
    ): Single<OWMForecastResponseType> =
        apiService.getForecastByGeographicCoordinates(coords.latitude, coords.longitude)

    override fun getWeatherDataByCityName(
        cityName: String,
        countryCode: String?
    ): Single<OWMForecastResponseType> {
        val cityNameRequest = if (countryCode != null) "$cityName,$countryCode" else cityName

        return apiService.getForecastByCityName(cityNameRequest)
    }

}
