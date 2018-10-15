package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.ICoordinates

open class OWMCoordinatesResponse(

    @SerializedName("lon")
    @Expose
    override var longitude: Double = 0.0,

    @SerializedName("lat")
    @Expose
    override var latitude: Double = 0.0

) : RealmObject(), ICoordinates