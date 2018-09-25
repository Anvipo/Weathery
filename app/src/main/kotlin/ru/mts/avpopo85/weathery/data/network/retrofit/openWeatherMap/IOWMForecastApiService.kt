package ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMForecastResponse
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OWMConstants
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OWMUnitsFormat
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OpenWeatherMapLanguages

@Suppress("unused")
interface IOWMForecastApiService {

    @GET("forecast")
    fun getForecastById(
        @Query("id")
        cityID: Int,

        @Query("units")
        units: String = OWMUnitsFormat.METRIC._name,

        @Query("lang")
        lang: String = OpenWeatherMapLanguages.RUSSIAN.lang_code,

        @Query("appid")
        appId: String = OWMConstants.API_ID
    ): Single<OWMForecastResponse>

    @GET("forecast")
    fun getCurrentWeatherByCityName(
        @Query("q")
        cityName: String,

        @Query("units")
        units: String = OWMUnitsFormat.METRIC._name,

        @Query("lang")
        lang: String = OpenWeatherMapLanguages.RUSSIAN.lang_code,

        @Query("appid")
        appId: String = OWMConstants.API_ID
    ): Single<OWMForecastResponse>

    @GET("forecast")
    fun getCurrentWeatherByGeographicCoordinates(
        @Query("lat")
        lat: String,

        @Query("lon")
        lon: String,

        @Query("units")
        units: String = OWMUnitsFormat.METRIC._name,

        @Query("lang")
        lang: String = OpenWeatherMapLanguages.RUSSIAN.lang_code,

        @Query("appid")
        appId: String = OWMConstants.API_ID
    ): Single<OWMForecastResponse>

    @GET("forecast")
    fun getCurrentWeatherByZipCode(
        @Query("zip")
        zip: String,

        @Query("units")
        units: String = OWMUnitsFormat.METRIC._name,

        @Query("lang")
        lang: String = OpenWeatherMapLanguages.RUSSIAN.lang_code,

        @Query("appid")
        appId: String = OWMConstants.API_ID
    ): Single<OWMForecastResponse>

}