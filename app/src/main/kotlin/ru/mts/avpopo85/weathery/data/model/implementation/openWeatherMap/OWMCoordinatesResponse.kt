package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.IOWMCoordinatesResponse

open class OWMCoordinatesResponse(

    @SerializedName("lon")
    @Expose
    override var longitude: Double = 0.0,

    @SerializedName("lat")
    @Expose
    override var latitude: Double = 0.0

) : RealmObject(), IOWMCoordinatesResponse