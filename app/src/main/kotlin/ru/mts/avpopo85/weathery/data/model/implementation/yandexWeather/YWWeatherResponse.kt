package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWCityInfoResponse
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWWeatherResponse
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.YWForecastListResponseType

data class YWWeatherResponse(

    @SerializedName("now")
    @Expose
    override val serverInUnix: Int,

    @SerializedName("now_dt")
    @Expose
    override val serverTimeInUTC: String,

    @SerializedName("info")
    @Expose
    override val cityInfoResponse: IYWCityInfoResponse,

    @SerializedName("fact")
    @Expose
    override val currentWeatherResponse: YWCurrentWeatherResponseType,

    @SerializedName("forecasts")
    @Expose
    override val forecasts: YWForecastListResponseType

) : IYWWeatherResponse