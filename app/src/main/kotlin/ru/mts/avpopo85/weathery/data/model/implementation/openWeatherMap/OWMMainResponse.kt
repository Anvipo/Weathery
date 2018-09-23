package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.IOWMMainResponse

open class OWMMainResponse(

    @SerializedName("temp")
    @Expose
    override var temperature: Double = 0.0,

    @SerializedName("pressure")
    @Expose
    override var atmosphericPressureInhPa: Int = 0,

    @SerializedName("humidity")
    @Expose
    override var humidity: Int = 0,

    @SerializedName("temp_min")
    @Expose
    override var minimumTemperature: Double = 0.0,

    @SerializedName("temp_max")
    @Expose
    override var maximumTemperature: Double = 0.0

) : RealmObject(), IOWMMainResponse