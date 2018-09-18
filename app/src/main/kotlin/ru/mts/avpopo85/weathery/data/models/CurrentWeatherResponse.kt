package ru.mts.avpopo85.weathery.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**This object contains information about the current weather.*/
data class CurrentWeatherResponse(

    /**Temperature (°С).*/
    @SerializedName("temp")
    @Expose
    val temperature: Double,

    /**What the temperature feels like (°С).*/
    @SerializedName("feels_like")
    @Expose
    val feelsLikeTemperature: Double,

    /**
     * The water temperature (°С).
     *
     * This parameter is returned for localities where this information is relevant.
     */
    @SerializedName("temp_water")
    @Expose
    val waterTemperature: Double,

    /**The code of the weather icon.
     *
     * The icon is available at
     * https://yastatic/weather/i/icons/blueye/color/svg/<value from the icon field>.svg.
     */
    @SerializedName("icon")
    @Expose
    val iconId: String,

    /**The code for the weather description.*/
    @SerializedName("condition")
    @Expose
    val weatherDescription: String,

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

    /**Light or dark time of the day.*/
    @SerializedName("daytime")
    @Expose
    val daytime: String,

    /**Polar day or polar night.*/
    @SerializedName("polar")
    @Expose
    val polar: Boolean,

    /**Time of year in this locality.*/
    @SerializedName("season")
    @Expose
    val season: String,

    /**The time when weather data was measured, in Unix time.*/
    @SerializedName("obs_time")
    @Expose
    val observationUnixTime: Int,

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
