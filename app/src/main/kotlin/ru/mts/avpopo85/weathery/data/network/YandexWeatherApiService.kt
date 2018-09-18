package ru.mts.avpopo85.weathery.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.data.models.WeatherResponse

interface YandexWeatherApiService {

    @Headers("X-Yandex-API-Key: $API_KEY")
    @GET("forecast")
    fun currentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String
    ): Single<WeatherResponse>

    @Headers("X-Yandex-API-Key: $API_KEY")
    @GET("forecast")
    fun forecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String,
        @Query("limit") dayNumberInForecast: Int,
        @Query("hours") withForecastForHours: Boolean,
        @Query("extra") withExtraInformation: Boolean
    ): Single<WeatherResponse>

    companion object {

        const val BASE_URL: String = "http://api.weather.yandex.ru/v1/"

        const val API_KEY: String = "c927ab88-f126-4d38-bebb-8857c4520861"

    }

}