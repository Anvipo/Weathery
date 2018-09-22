package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

/**Object with a 12-hour forecast for the day or night.*/
open class YWDayShortResponse(

    /**Highest daytime or lowest nighttime temperature (°C).*/
    @SerializedName("temp")
    @Expose
    var temperature: Double = 0.0,

    /**What the temperature feels like (°C).*/
    @SerializedName("feels_like")
    @Expose
    var feelsLikeTemperature: Double = 0.0,

    /**The code of the weather icon.
     *
     * The icon is available at
     * https://yastatic.net/weather/i/icons/blueye/color/svg/<value from the icon field>.svg.*/
    @SerializedName("icon")
    @Expose
    var iconId: String = "",

    /**The code for the weather description.*/
    @SerializedName("condition")
    @Expose
    var condition: String = "",

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

    /**Type of precipitation.*/
    @SerializedName("prec_type")
    @Expose
    var precipitationType: Int,

    /**Intensity of precipitation.*/
    @SerializedName("prec_strength")
    @Expose
    var precipitationStrength: Double = 0.0,

    /**Cloud cover.*/
    @SerializedName("cloudness")
    @Expose
    var cloudiness: Double = 0.0

) : RealmObject()

