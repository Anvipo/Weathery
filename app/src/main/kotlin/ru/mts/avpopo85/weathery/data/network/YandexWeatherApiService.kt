package ru.mts.avpopo85.weathery.data.network

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.models.weather.yandex.WeatherResponse

interface YandexWeatherApiService {
    @Headers("X-Yandex-API-Key: $API_KEY")
    @GET("forecast")
    fun forecast(
            @Query("lat") latitude: Double = 45.03,
            @Query("lon") longitude: Double = 38.98,
            @Query("lang") language: String = "ru_RU",
            @Query("limit") dayNumberInForecast: Int = 7,
            @Query("hours") withForecastForHours: String = "true",
            @Query("extra") withExtraInformation: String = "true"
    ): Single<WeatherResponse>

    @Headers("X-Yandex-API-Key: $API_KEY")
    @GET("forecast")
    fun forecast2(
            @Query("lat") latitude: Double = 45.03,
            @Query("lon") longitude: Double = 38.98,
            @Query("lang") language: String = "ru_RU",
            @Query("limit") dayNumberInForecast: Int = 7,
            @Query("hours") withForecastForHours: String = "true",
            @Query("extra") withExtraInformation: String = "true"
    ): Observable<WeatherResponse>

    @Headers("X-Yandex-API-Key: $API_KEY")
    @GET("forecast")
    fun forecast3(
            @Query("lat") latitude: Double = 45.03,
            @Query("lon") longitude: Double = 38.98,
            @Query("lang") language: String = "ru_RU",
            @Query("limit") dayNumberInForecast: Int = 7,
            @Query("hours") withForecastForHours: String = "true",
            @Query("extra") withExtraInformation: String = "true"
    ): Maybe<WeatherResponse>

    @Headers("X-Yandex-API-Key: $API_KEY")
    @GET("forecast")
    fun forecast4(
            @Query("lat") latitude: Double = 45.03,
            @Query("lon") longitude: Double = 38.98,
            @Query("lang") language: String = "ru_RU",
            @Query("limit") dayNumberInForecast: Int = 7,
            @Query("hours") withForecastForHours: String = "true",
            @Query("extra") withExtraInformation: String = "true"
    ): Completable

    companion object {
        const val BASE_URL = "http://api.weather.yandex.ru/v1/"
        const val API_KEY = "c927ab88-f126-4d38-bebb-8857c4520861"
    }
}