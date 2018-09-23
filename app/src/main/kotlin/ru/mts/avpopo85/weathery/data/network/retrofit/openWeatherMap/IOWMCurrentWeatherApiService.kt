package ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.IOWMCurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.OWMConstants.API_ID
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OWMUnitsFormat
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OpenWeatherMapLanguages
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType

@Suppress("SpellCheckingInspection", "unused")
interface IOWMCurrentWeatherApiService {

    @GET("weather")
    fun getCurrentWeatherById(
        @Query("id")
        cityID: Int,

        @Query("units")
        units: String = OWMUnitsFormat.METRIC._name,

        @Query("lang")
        lang: String = OpenWeatherMapLanguages.RUSSIAN.lang_code,

        @Query("appid")
        appId: String = API_ID
    ): Single<OWMCurrentWeatherResponseType>

    @GET("weather")
    fun getCurrentWeatherForSeveralIDs(
        @Query("id")
        cityIDs: List<Int>,

        @Query("units")
        units: String = OWMUnitsFormat.METRIC._name,

        @Query("lang")
        lang: String = OpenWeatherMapLanguages.RUSSIAN.lang_code,

        @Query("appid")
        appId: String = API_ID
    ): Single<IOWMCurrentWeatherResponse>

    @GET("weather")
    fun getCurrentWeatherByCityName(
        @Query("q")
        cityName: String,

        @Query("units")
        units: String = OWMUnitsFormat.METRIC._name,

        @Query("lang")
        lang: String = OpenWeatherMapLanguages.RUSSIAN.lang_code,

        @Query("appid")
        appId: String = API_ID
    ): Single<IOWMCurrentWeatherResponse>

    @GET("weather")
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
        appId: String = API_ID
    ): Single<IOWMCurrentWeatherResponse>

    @GET("weather")
    fun getCurrentWeatherByZipCode(
        @Query("zip")
        zip: String,

        @Query("units")
        units: String = OWMUnitsFormat.METRIC._name,

        @Query("lang")
        lang: String = OpenWeatherMapLanguages.RUSSIAN.lang_code,

        @Query("appid")
        appId: String = API_ID
    ): Single<IOWMCurrentWeatherResponse>

}