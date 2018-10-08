package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.currentWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.annotations.PrimaryKey
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.currentWeather.IYWCurrentWeatherRealmResponse

/**This object contains information about the current weather.*/
open class YWCurrentWeatherResponse(

    @SerializedName("temp")
    @Expose
    override var temperature: Double = 0.0,

    @SerializedName("feels_like")
    @Expose
    override var feelsLikeTemperature: Double = 0.0,

    @SerializedName("temp_water")
    @Expose
    override var waterTemperature: Double = 0.0,

    @SerializedName("icon")
    @Expose
    override var iconId: String = "",

    @SerializedName("condition")
    @Expose
    override var weatherDescription: String = "",

    @SerializedName("wind_speed")
    @Expose
    override var windSpeed: Double = 0.0,

    @SerializedName("wind_gust")
    @Expose
    override var windGustsSpeed: Double = 0.0,

    @SerializedName("wind_dir")
    @Expose
    override var windDirection: String = "",

    @SerializedName("pressure_mm")
    @Expose
    override var atmosphericPressureInMmHg: Double = 0.0,

    @SerializedName("pressure_pa")
    @Expose
    override var atmosphericPressureInhPa: Double = 0.0,

    @SerializedName("humidity")
    @Expose
    override var humidity: Double = 0.0,

    @SerializedName("daytime")
    @Expose
    override var daytime: String = "",

    @SerializedName("polar")
    @Expose
    override var polar: Boolean = false,

    @SerializedName("season")
    @Expose
    override var season: String = "",

    @PrimaryKey
    @SerializedName("obs_time")
    @Expose
    override var observationUnixTime: Long = 0,

    @SerializedName("prec_type")
    @Expose
    override var precipitationType: Int = 0,

    @SerializedName("prec_strength")
    @Expose
    override var precipitationStrength: Double = 0.0,

    @SerializedName("cloudness")
    @Expose
    override var cloudiness: Double = 0.0

) : IYWCurrentWeatherRealmResponse
