package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWDayShortResponse

/**Object with a 12-hour forecast for the day or night.*/
@Suppress("SpellCheckingInspection")
open class YWDayShortResponse(

    @SerializedName("temp")
    @Expose
    override var temperature: Double = 0.0,

    @SerializedName("feels_like")
    @Expose
    override var feelsLikeTemperature: Double = 0.0,

    @SerializedName("icon")
    @Expose
    override var iconId: String = "",

    @SerializedName("condition")
    @Expose
    override var condition: String = "",

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

    @SerializedName("prec_type")
    @Expose
    override var precipitationType: Int = 0,

    @SerializedName("prec_strength")
    @Expose
    override var precipitationStrength: Double = 0.0,

    @SerializedName("cloudness")
    @Expose
    override var cloudiness: Double = 0.0

) : RealmObject(), IYWDayShortResponse

