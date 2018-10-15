package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.currentWeather.IOWMCurrentWeatherMainResponse

open class OWMCurrentWeatherMainResponse(

    @SerializedName("temp")
    @Expose
    override var temperature: Double = 0.0,

    @SerializedName("pressure")
    @Expose
    override var atmosphericPressureInhPa: Double = 0.0,

    @SerializedName("humidity")
    @Expose
    override var humidity: Double = 0.0,

    @SerializedName("temp_min")
    @Expose
    override var minimumTemperature: Double = 0.0,

    @SerializedName("temp_max")
    @Expose
    override var maximumTemperature: Double = 0.0

) : RealmObject(), IOWMCurrentWeatherMainResponse
