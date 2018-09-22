package ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWForecastParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWWeatherResponse
import ru.mts.avpopo85.weathery.data.network.base.ForecastApiService
import ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather.YWConstants.API_KEY

interface YWForecastApiService :
    ForecastApiService<YWWeatherResponse, YWForecastParameters> {

    override fun getForecast(parameters: YWForecastParameters): Single<YWWeatherResponse> =
        forecast(
            parameters.latitude,
            parameters.longitude,
            parameters.language,
            parameters.dayNumberInForecast,
            parameters.withForecastForHours,
            parameters.withExtraInformation
        )

    @Headers("X-Yandex-API-Key: $API_KEY")
    @GET("forecast")
    fun forecast(
        @Query("lat")
        latitude: Double,

        @Query("lon")
        longitude: Double,

        @Query("lang")
        language: String,

        @Query("limit")
        dayNumberInForecast: Int,

        @Query("YWHours")
        withForecastForHours: Boolean,

        @Query("extra")
        withExtraInformation: Boolean
    ): Single<YWWeatherResponse>

}