package ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMForecastResponse
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMForecastApiService
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.weather.common.AbsForecastRepository
import ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.base.IOWMRepository
import ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.utils.TripleOfOWMApiCalls
import ru.mts.avpopo85.weathery.data.repository.weather.utils.onUnknownCurrentLocation
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
    locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context
) :
    AbsForecastRepository<OWMListItemResponseType>(
        networkManager, forecastDbService, locationDbService, context
    ),
    IForecastRepository<OWMForecastListResponseType>,
    IOWMRepository<OWMForecastResponseType> {

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
                    it.apply { cityName = forecastResponse.city.name }
                }
            }
    }

    override fun make3ApiCalls(): TripleOfOWMApiCalls<OWMForecastResponseType> {
        val currentAddress: UserAddressType = getLastKnownAddress()

        val coords: GeographicCoordinates? = currentAddress.coords
        val cityName: String? = currentAddress.locality
        val countryCode: String? = currentAddress.countryCode
        val postalCode: Int? = currentAddress.postalCode

        val geoCoordsApiCall = makeGeoCoordsApiCall(coords)
        val cityNameApiCall = makeCityNameApiCall(cityName, countryCode)
        val postalCodeApiCall = makePostalCodeApiCall(postalCode, countryCode)

        return TripleOfOWMApiCalls(geoCoordsApiCall, postalCodeApiCall, cityNameApiCall)
    }

    override fun makeApiCall(
        firstApiCall: Single<OWMForecastResponseType>?,
        secondApiCall: Single<OWMForecastResponseType>?,
        thirdApiCall: Single<OWMForecastResponseType>?
    ): Single<OWMForecastResponseType> =
        when {
            //Priority order
            //The order is important
            //DONT CHANGE ANY ORDER
            firstApiCall != null ->
                firstApiCallWithErrorCatching(firstApiCall, secondApiCall, thirdApiCall)
            secondApiCall != null ->
                secondApiCallWithErrorCatching(secondApiCall, thirdApiCall)
            thirdApiCall != null -> thirdApiCallWithErrorCatching(thirdApiCall)
            else -> onUnknownLocation()
        }

    override fun makePostalCodeApiCall(
        postalCode: Int?,
        countryCode: String?
    ): Single<OWMForecastResponse>? =
        if (postalCode != null) {
            getWeatherDataByZipCode(postalCode, countryCode)
        } else {
            null
        }

    override fun makeCityNameApiCall(
        cityName: String?,
        countryCode: String?
    ): Single<OWMForecastResponse>? =
        if (cityName != null) {
            getWeatherDataByCityName(cityName, countryCode)
        } else {
            null
        }

    override fun makeGeoCoordsApiCall(coords: GeographicCoordinates?): Single<OWMForecastResponseType>? =
        if (coords.areNotNull()) {
            getWeatherDataByGeographicCoordinates(coords!!)
        } else {
            null
        }

    override fun getWeatherDataByZipCode(
        zipCode: Int,
        countryCode: String?
    ): Single<OWMForecastResponseType> {
        val zip = if (countryCode != null) "$zipCode,$countryCode" else "$zipCode"

        return apiService.getForecastByZipCode(zip)
    }

    override fun getWeatherDataByGeographicCoordinates(
        coords: GeographicCoordinates
    ): Single<OWMForecastResponseType> =
        apiService.getForecastByGeographicCoordinates(coords.latitude!!, coords.longitude!!)

    override fun getWeatherDataByCityName(
        cityName: String,
        countryCode: String?
    ): Single<OWMForecastResponseType> {
        val cityNameRequest = if (countryCode != null) "$cityName,$countryCode" else cityName

        return apiService.getForecastByCityName(cityNameRequest)
    }

    override fun firstApiCallWithErrorCatching(
        first: Single<OWMForecastResponseType>,
        second: Single<OWMForecastResponseType>?,
        third: Single<OWMForecastResponseType>?
    ): Single<OWMForecastResponseType> =
        first.onErrorResumeNext {
            when {
                second != null ->
                    secondApiCallWithErrorCatching(second, third)
                third != null -> thirdApiCallWithErrorCatching(third)
                else -> onUnknownLocation()
            }
        }

    override fun secondApiCallWithErrorCatching(
        second: Single<OWMForecastResponseType>,
        third: Single<OWMForecastResponseType>?
    ): Single<OWMForecastResponseType> =
        second.onErrorResumeNext {
            if (third != null) {
                thirdApiCallWithErrorCatching(third)
            } else {
                onUnknownLocation()
            }
        }

    override fun thirdApiCallWithErrorCatching(third: Single<OWMForecastResponseType>)
            : Single<OWMForecastResponseType> = third.onErrorResumeNext { onUnknownLocation() }

    override fun onUnknownLocation(): Single<OWMForecastResponseType> =
        context.onUnknownCurrentLocation()

}
