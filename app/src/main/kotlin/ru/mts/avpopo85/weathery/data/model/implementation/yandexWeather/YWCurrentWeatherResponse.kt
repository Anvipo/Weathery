package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.CurrentWeatherResponse

/**This object contains information about the current weather.*/
open class YWCurrentWeatherResponse(

    /**Temperature (°С).*/
    @SerializedName("temp")
    @Expose
    override var temperature: Double = 0.0,

    /**What the temperature feels like (°С).*/
    @SerializedName("feels_like")
    @Expose
    var feelsLikeTemperature: Double = 0.0,

    /**
     * The water temperature (°С).
     *
     * This parameter is returned for localities where this information is relevant.
     */
    @SerializedName("temp_water")
    @Expose
    var waterTemperature: Double = 0.0,

    /**The code of the weather icon.
     *
     * The icon is available at
     * https://yastatic/weather/i/icons/blueye/color/svg/<value from the icon field>.svg.
     */
    @SerializedName("icon")
    @Expose
    var iconId: String = "",

    /**The code for the weather description.*/
    @SerializedName("condition")
    @Expose
    var weatherDescription: String,

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

    /**Light or dark time of the day.*/
    @SerializedName("daytime")
    @Expose
    var daytime: String = "",

    /**Polar day or polar night.*/
    @SerializedName("polar")
    @Expose
    var polar: Boolean = false,

    /**Time of year in this locality.*/
    @SerializedName("season")
    @Expose
    var season: String = "",

    /**The time when weather data was measured, in Unix time.*/
    @SerializedName("obs_time")
    @Expose
    var observationUnixTime: Int = 0,

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

) : RealmObject(), CurrentWeatherResponse
