package ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMForecastResponse
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.utils.OWMUnitsFormat
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.utils.OpenWeatherMapLanguages
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMConstants

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
    fun getForecastByCityName(
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
    fun getForecastByGeographicCoordinates(
        @Query("lat")
        lat: Double,

        @Query("lon")
        lon: Double,

        @Query("units")
        units: String = OWMUnitsFormat.METRIC._name,

        @Query("lang")
        lang: String = OpenWeatherMapLanguages.RUSSIAN.lang_code,

        @Query("appid")
        appId: String = OWMConstants.API_ID
    ): Single<OWMForecastResponse>

    @GET("forecast")
    fun getForecastByZipCode(
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
