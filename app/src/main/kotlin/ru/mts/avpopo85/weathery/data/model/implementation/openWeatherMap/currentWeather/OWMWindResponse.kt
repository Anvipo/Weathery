package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.currentWeather.IOWMWindResponse

open class OWMWindResponse(

    @SerializedName("speed")
    @Expose
    override var speed: Double = 0.0,

    @SerializedName("deg")
    @Expose
    override var directionInDegrees: Double = 0.0

) : RealmObject(), IOWMWindResponse