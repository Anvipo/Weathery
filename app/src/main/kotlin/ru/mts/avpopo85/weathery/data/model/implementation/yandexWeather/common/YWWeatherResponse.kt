package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.common.IYWWeatherResponse
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType

@Suppress("unused")
data class YWWeatherResponse(

    @SerializedName("now")
    @Expose
    override val serverInUnix: Int,

    @SerializedName("now_dt")
    @Expose
    override val serverTimeInUTC: String,

    @SerializedName("info")
    @Expose
    override val cityInfoResponse: YWCityInfoResponse,

    @SerializedName("fact")
    @Expose
    override val currentWeatherResponse: YWCurrentWeatherResponseType,

    @SerializedName("forecasts")
    @Expose
    override val forecasts: YWForecastListResponseType

) : IYWWeatherResponse