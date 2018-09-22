package ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWCurrentWeatherParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWWeatherResponse
import ru.mts.avpopo85.weathery.data.network.base.CurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather.YWConstants.API_KEY

interface YWCurrentWeatherApiService :
    CurrentWeatherApiService<YWWeatherResponse, YWCurrentWeatherParameters> {

    override fun getCurrentWeather(parameters: YWCurrentWeatherParameters): Single<YWWeatherResponse> =
        currentWeather(parameters.latitude, parameters.longitude, parameters.language)

    @Headers("X-Yandex-API-Key: $API_KEY")
    @GET("forecast")
    fun currentWeather(
        @Query("lat")
        latitude: Double,

        @Query("lon")
        longitude: Double,

        @Query("lang")
        language: String
    ): Single<YWWeatherResponse>

}