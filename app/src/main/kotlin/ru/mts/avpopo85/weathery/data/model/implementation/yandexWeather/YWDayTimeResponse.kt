package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

/**Object with the weather forecast for the time of the day*/
open class YWDayTimeResponse(

    /**Minimum temperature for the time of day (°C).*/
    @SerializedName("temp_min")
    @Expose
    var temperatureMinimum: Double = 0.0,

    /**Maximum temperature for the time of day (°C).*/
    @SerializedName("temp_max")
    @Expose
    var temperatureMaximum: Double = 0.0,

    /**Average temperature for the time of day (°C).*/
    @SerializedName("temp_avg")
    @Expose
    var temperatureAverage: Double = 0.0,

    /**What the temperature feels like (°C).*/
    @SerializedName("feels_like")
    @Expose
    var feelsLikeTemperature: Double = 0.0,

    /**The code of the weather icon.
     *
     * The icon is available at
     * https://yastatic.net/weather/i/icons/blueye/color/svg/<value from the icon field>.svg.
     */
    @SerializedName("icon")
    @Expose
    var iconId: String = "",

    /**The code for the weather description.*/
    @SerializedName("condition")
    @Expose
    var condition: String = "",

    /**Light or dark time of the day.*/
    @SerializedName("daytime")
    @Expose
    var daytime: String = "",

    /**Polar day or polar night.*/
    @SerializedName("polar")
    @Expose
    var polar: Boolean = false,

    /**Wind speed (meters per second).*/
    @SerializedName("wind_speed")
    @Expose
    var windSpeed: Double = 0.0,

    /**Speed of wind gusts (meters per second).*/
    @SerializedName("wind_gust")
    @Expose
    var windGustsSpeed: Double = 0.0,

    /**Wind direction.*/
    @SerializedName("wind_dir")
    @Expose
    var windDirection: String = "",

    /**Atmospheric pressure (mm Hg).*/
    @SerializedName("pressure_mm")
    @Expose
    var atmosphericPressureInMmHg: Double = 0.0,

    /**Atmospheric pressure (hPa).*/
    @SerializedName("pressure_pa")
    @Expose
    var atmosphericPressureInhPa: Double = 0.0,

    /**Humidity (percent).*/
    @SerializedName("humidity")
    @Expose
    var humidity: Double = 0.0,

    /**Predicted amount of precipitation (mm).*/
    @SerializedName("prec_mm")
    @Expose
    var precipitationInMm: Double = 0.0,

    /**Predicted duration of precipitation (minutes).*/
    @SerializedName("prec_period")
    @Expose
    var precipitationInMinutes: Double = 0.0,

    /**Type of precipitation.*/
    @SerializedName("prec_type")
    @Expose
    var precipitationType: Int = 0,

    /**Intensity of precipitation.*/
    @SerializedName("prec_strength")
    @Expose
    var precipitationStrength: Double = 0.0,

    /**Cloud cover.*/
    @SerializedName("cloudness")
    @Expose
    var cloudiness: Double = 0.0

) : RealmObject()
