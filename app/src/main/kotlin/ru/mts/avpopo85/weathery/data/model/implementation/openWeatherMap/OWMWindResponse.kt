package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.IOWMWindResponse

open class OWMWindResponse(

    @SerializedName("speed")
    @Expose
    override var speed: Int = 0,

    @SerializedName("deg")
    @Expose
    override var directionInDegrees: Int = 0

) : RealmObject(), IOWMWindResponse