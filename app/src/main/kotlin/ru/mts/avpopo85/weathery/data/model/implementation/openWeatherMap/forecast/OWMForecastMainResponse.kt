package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMForecastMainResponse

@Suppress("SpellCheckingInspection")
data class OWMForecastMainResponse(

    @SerializedName("temp")
    @Expose
    override val temperature: Double,

    @SerializedName("temp_min")
    @Expose
    override val minimumTemperature: Double,

    @SerializedName("grnd_level")
    @Expose
    override val atmosphericPressureOnTheGroundLevelInhPa: Double,

    @SerializedName("temp_kf")
    @Expose
    override val tempKf: Double,

    @SerializedName("humidity")
    @Expose
    override val humidity: Double,

    @SerializedName("pressure")
    @Expose
    override val atmosphericPressureOnTheSeaLevelByDefaultInhPa: Double,

    @SerializedName("sea_level")
    @Expose
    override val atmosphericPressureOnTheSeaLevelInhPa: Double,

    @SerializedName("temp_max")
    @Expose
    override val maximumTemperature: Double

) : IOWMForecastMainResponse
