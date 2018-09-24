package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.IOWMCloudsResponse

open class OWMCloudsResponse(

    @SerializedName("all")
    @Expose
    override var cloudiness: Int = 0

) : RealmObject(), IOWMCloudsResponse