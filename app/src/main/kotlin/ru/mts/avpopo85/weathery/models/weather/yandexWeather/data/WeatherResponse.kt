package ru.mts.avpopo85.weathery.models.weather.yandexWeather.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
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
    val cityInfoResponse: CityInfoResponse,

    /**Current weather information object.	*/
    @SerializedName("fact")
    @Expose
    val currentWeatherResponse: CurrentWeatherResponse,

    /**Weather forecast object.*/
    @SerializedName("forecasts")
    @Expose
    val forecasts: List<ForecastResponse>
)