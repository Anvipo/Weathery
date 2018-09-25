package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMForecastMainResponse

@Suppress("SpellCheckingInspection")
open class OWMForecastMainResponse(

    @SerializedName("temp")
    @Expose
    override var temperature: Double = 0.0,

    @SerializedName("temp_min")
    @Expose
    override var minimumTemperature: Double = 0.0,

    @SerializedName("grnd_level")
    @Expose
    override var atmosphericPressureOnTheGroundLevelInhPa: Double = 0.0,

    @SerializedName("temp_kf")
    @Expose
    override var tempKf: Double = 0.0,

    @SerializedName("humidity")
    @Expose
    override var humidity: Double = 0.0,

    @SerializedName("pressure")
    @Expose
    override var atmosphericPressureOnTheSeaLevelByDefaultInhPa: Double = 0.0,

    @SerializedName("sea_level")
    @Expose
    override var atmosphericPressureOnTheSeaLevelInhPa: Double = 0.0,

    @SerializedName("temp_max")
    @Expose
    override var maximumTemperature: Double = 0.0

) : RealmObject(), IOWMForecastMainResponse
