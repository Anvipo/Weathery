package ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IBaseWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.utils.TripleOfOWMApiCalls

interface IOWMRepository<T : IBaseWeatherResponse> {

    fun getWeatherDataByCityName(cityName: String, countryCode: String?): Single<T>

    fun getWeatherDataByGeographicCoordinates(coords: GeographicCoordinates): Single<T>

    fun getWeatherDataByZipCode(postalCode: String, countryCode: String?): Single<T>

    fun makeGeoCoordsApiCall(coords: GeographicCoordinates?): Single<T>?

    fun makeCityNameApiCall(cityName: String?, countryCode: String?): Single<T>?

    fun makePostalCodeApiCall(postalCode: String?, countryCode: String?): Single<T>?

    fun make3ApiCalls(): TripleOfOWMApiCalls<T>

//    fun makeApiCall(
//        firstApiCall: Single<T>?,
//        secondApiCall: Single<T>?,
//        thirdApiCall: Single<T>?
//    ): Single<T>

//    fun onUnknownLocation(): Single<T>

//    fun firstApiCallWithErrorCatching(
//        first: Single<T>,
//        second: Single<T>?,
//        third: Single<T>?
//    ): Single<T>
//
//    fun secondApiCallWithErrorCatching(second: Single<T>, third: Single<T>?): Single<T>
//
//    fun thirdApiCallWithErrorCatching(third: Single<T>): Single<T>

}
