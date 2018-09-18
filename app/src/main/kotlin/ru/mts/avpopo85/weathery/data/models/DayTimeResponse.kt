package ru.mts.avpopo85.weathery.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**Object with the weather forecast for the time of the day*/
data class DayTimeResponse(

    /**Minimum temperature for the time of day (°C).*/
    @SerializedName("temp_min")
    @Expose
    val temperatureMinimum: Double,

    /**Maximum temperature for the time of day (°C).*/
    @SerializedName("temp_max")
    @Expose
    val temperatureMaximum: Double,

    /**Average temperature for the time of day (°C).*/
    @SerializedName("temp_avg")
    @Expose
    val temperatureAverage: Double,

    /**What the temperature feels like (°C).*/
    @SerializedName("feels_like")
    @Expose
    val feelsLikeTemperature: Double,

    /**The code of the weather icon.
     *
     * The icon is available at
     * https://yastatic.net/weather/i/icons/blueye/color/svg/<value from the icon field>.svg.
     */
    @SerializedName("icon")
    @Expose
    val iconId: String,

    /**The code for the weather description.*/
    @SerializedName("condition")
    @Expose
    val condition: String,

    /**Light or dark time of the day.*/
    @SerializedName("daytime")
    @Expose
    val daytime: String,

    /**Polar day or polar night.*/
    @SerializedName("polar")
    @Expose
    val polar: Boolean,

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
