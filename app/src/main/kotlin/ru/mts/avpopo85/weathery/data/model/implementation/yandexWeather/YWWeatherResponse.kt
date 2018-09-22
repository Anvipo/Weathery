package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.WeatherResponse
import ru.mts.avpopo85.weathery.utils.CurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.ForecastListResponseType

data class YWWeatherResponse(

    /**The time on the server in Unix time.*/
    @SerializedName("now")
    @Expose
    val serverInUnix: Int,

    /**The time on the server in UTC.*/
    @SerializedName("now_dt")
    @Expose
    val serverTimeInUTC: String,

    /**Locality information object.*/
    @SerializedName("info")
    @Expose
    val cityInfoResponse: YWCityInfoResponse,

    /**Current weather information object.	*/
    @SerializedName("fact")
    @Expose
    val currentWeatherResponse: CurrentWeatherResponseType,

    /**Weather forecast object.*/
    @SerializedName("forecasts")
    @Expose
    val forecasts: ForecastListResponseType

) : WeatherResponse