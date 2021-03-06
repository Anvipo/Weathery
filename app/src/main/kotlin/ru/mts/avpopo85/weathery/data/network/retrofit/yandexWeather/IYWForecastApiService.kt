package ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWConstants.API_KEY
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType

interface IYWForecastApiService {

    @Headers("X-Yandex-API-Key: $API_KEY")
    @GET("forecast")
    fun getForecast(
        @Query("lat")
        latitude: Double,

        @Query("lon")
        longitude: Double,

        @Query("lang")
        language: String,

        @Query("limit")
        dayNumberInForecast: Int,

        @Query("hours")
        withForecastForHours: Boolean,

        @Query("extra")
        withExtraInformation: Boolean
    ): Single<YWForecastListResponseType>

}
