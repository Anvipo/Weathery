package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast.IYWDayTimeResponse

/**Object with the weather forecast for the time of the day*/
open class YWDayTimeResponse(

    @SerializedName("temp_min")
    @Expose
    override var temperatureMinimum: Double = 0.0,

    @SerializedName("temp_max")
    @Expose
    override var temperatureMaximum: Double = 0.0,

    @SerializedName("temp_avg")
    @Expose
    override var temperatureAverage: Double = 0.0,

    @SerializedName("feels_like")
    @Expose
    override var feelsLikeTemperature: Double = 0.0,

    @SerializedName("icon")
    @Expose
    override var iconId: String = "",

    @SerializedName("condition")
    @Expose
    override var condition: String = "",

    @SerializedName("daytime")
    @Expose
    override var daytime: String = "",

    @SerializedName("polar")
    @Expose
    override var polar: Boolean = false,

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

    @SerializedName("prec_mm")
    @Expose
    override var precipitationInMm: Double = 0.0,

    @SerializedName("prec_period")
    @Expose
    override var precipitationInMinutes: Double = 0.0,

    @SerializedName("prec_type")
    @Expose
    override var precipitationType: Int = 0,

    @SerializedName("prec_strength")
    @Expose
    override var precipitationStrength: Double = 0.0,

    @SerializedName("cloudness")
    @Expose
    override var cloudiness: Double = 0.0

) : RealmObject(),
    IYWDayTimeResponse
