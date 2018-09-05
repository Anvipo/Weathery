package ru.mts.avpopo85.weathery.data.network

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.WeatherResponse

interface YandexWeatherApiService {
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
        const val BASE_URL = "http://api.weather.yandex.ru/v1/"
        const val API_KEY = "c927ab88-f126-4d38-bebb-8857c4520861"
    }
}