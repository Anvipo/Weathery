package ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.weather.common.AbsCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.base.IOWMRepository
import ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.utils.TripleOfOWMApiCalls
import ru.mts.avpopo85.weathery.data.repository.weather.utils.onUnknownCurrentLocation
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
    ICurrentWeatherRepository<OWMCurrentWeatherResponseType>,
    IOWMRepository<OWMCurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<OWMCurrentWeatherResponseType> =
        getCurrentWeatherHelper()

    override fun makeApiCall(): Single<OWMCurrentWeatherResponseType> {
        val (
                firstApiCall,
                secondApiCall,
                thirdApiCall
        ) = make3ApiCalls()

        return makeApiCall(firstApiCall, secondApiCall, thirdApiCall)
    }

    override fun make3ApiCalls(): TripleOfOWMApiCalls<OWMCurrentWeatherResponseType> {
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
        firstApiCall: Single<OWMCurrentWeatherResponseType>?,
        secondApiCall: Single<OWMCurrentWeatherResponseType>?,
        thirdApiCall: Single<OWMCurrentWeatherResponseType>?
    ): Single<OWMCurrentWeatherResponseType> =
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

    override fun firstApiCallWithErrorCatching(
        first: Single<OWMCurrentWeatherResponseType>,
        second: Single<OWMCurrentWeatherResponseType>?,
        third: Single<OWMCurrentWeatherResponseType>?
    ): Single<OWMCurrentWeatherResponseType> =
        first.onErrorResumeNext {
            when {
                second != null ->
                    secondApiCallWithErrorCatching(second, third)
                third != null -> thirdApiCallWithErrorCatching(third)
                else -> onUnknownLocation()
            }
        }

    override fun secondApiCallWithErrorCatching(
        second: Single<OWMCurrentWeatherResponseType>,
        third: Single<OWMCurrentWeatherResponseType>?
    ): Single<OWMCurrentWeatherResponseType> =
        second.onErrorResumeNext {
            if (third != null) {
                thirdApiCallWithErrorCatching(third)
            } else {
                onUnknownLocation()
            }
        }

    override fun thirdApiCallWithErrorCatching(third: Single<OWMCurrentWeatherResponseType>)
            : Single<OWMCurrentWeatherResponseType> =
        third.onErrorResumeNext { onUnknownLocation() }

    override fun makePostalCodeApiCall(
        postalCode: Int?,
        countryCode: String?
    ): Single<OWMCurrentWeatherResponseType>? =
        if (postalCode != null) {
            getWeatherDataByZipCode(postalCode, countryCode)
        } else {
            null
        }

    override fun makeCityNameApiCall(
        cityName: String?,
        countryCode: String?
    ): Single<OWMCurrentWeatherResponseType>? =
        if (cityName != null) {
            getWeatherDataByCityName(cityName, countryCode)
        } else {
            null
        }

    override fun makeGeoCoordsApiCall(coords: GeographicCoordinates?): Single<OWMCurrentWeatherResponseType>? =
        if (coords.areNotNull()) {
            getWeatherDataByGeographicCoordinates(coords!!)
        } else {
            null
        }

    override fun getWeatherDataByZipCode(
        zipCode: Int,
        countryCode: String?
    ): Single<OWMCurrentWeatherResponseType> {
        val zipCodeRequest = if (countryCode != null) "$zipCode,$countryCode" else "$zipCode"

        return apiService.getCurrentWeatherByZipCode(zipCodeRequest)
    }

    override fun getWeatherDataByGeographicCoordinates(
        coords: GeographicCoordinates
    ): Single<OWMCurrentWeatherResponseType> =
        apiService.getCurrentWeatherByGeographicCoordinates(coords.latitude!!, coords.longitude!!)

    override fun getWeatherDataByCityName(
        cityName: String,
        countryCode: String?
    ): Single<OWMCurrentWeatherResponseType> {
        val cityNameRequest = if (countryCode != null) "$cityName,$countryCode" else cityName

        return apiService.getCurrentWeatherByCityName(cityNameRequest)
    }

    override fun onUnknownLocation(): Single<OWMCurrentWeatherResponseType> =
        context.onUnknownCurrentLocation()

}
