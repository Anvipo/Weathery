package ru.mts.avpopo85.weathery.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**Object for the hourly forecast. Contains 24 parts.*/
data class HourInfoResponse(

    /**The hour the forecast is for (0-23) using the local time.*/
    @SerializedName("hour")
    @Expose
    val hourInLocalTime: String,

    /**The time of the forecast in Unix time.*/
    @SerializedName("hour_ts")
    @Expose
    val hourInUnixTime: Int,

    /**Temperature (°C).*/
    @SerializedName("temp")
    @Expose
    val temperature: Double,

    /**What the temperature feels like (°C).*/
    @SerializedName("feels_like")
    @Expose
    val feelsLikeTemperature: Double,

    /**The code of the weather icon.
     *
     * The icon is available at
     * https://yastatic.net/weather/i/icons/blueye/color/svg/<value from the icon field>.svg.
     * */
    @SerializedName("icon")
    @Expose
    val iconId: String,

    /**The code for the weather description.*/
    @SerializedName("condition")
    @Expose
    val condition: String,

    /**Wind speed (meters per second).*/
    @SerializedName("wind_speed")
    @Expose
    val windSpeed: Double,

    /**Speed of wind gusts (meters per second).*/
    @SerializedName("wind_gust")
    @Expose
    val windGustsSpeed: Double,

    /**Wind direction.*/
    @SerializedName("wind_dir")
    @Expose
    val windDirection: String,

    /**Atmospheric pressure (mm Hg).*/
    @SerializedName("pressure_mm")
    @Expose
    val atmosphericPressureInMmHg: Double,

    /**Atmospheric pressure (hPa).*/
    @SerializedName("pressure_pa")
    @Expose
    val atmosphericPressureInhPa: Double,

    /**Humidity (percent).*/
    @SerializedName("humidity")
    @Expose
    val humidity: Double,

    /**Predicted amount of precipitation (mm).*/
    @SerializedName("prec_mm")
    @Expose
    val precipitationInMm: Double,

    /**Predicted duration of precipitation (minutes).*/
    @SerializedName("prec_period")
    @Expose
    val precipitationInMinutes: Double,

    /**Type of precipitation.*/
    @SerializedName("prec_type")
    @Expose
    val precipitationType: Int,

    /**Intensity of precipitation.*/
    @SerializedName("prec_strength")
    @Expose
    val precipitationStrength: Double,

    /**Cloud cover.*/
    @SerializedName("cloudness")
    @Expose
    val cloudiness: Double

)
