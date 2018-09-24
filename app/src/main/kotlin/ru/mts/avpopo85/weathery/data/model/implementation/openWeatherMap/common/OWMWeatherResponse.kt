package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.common.IOWMWeatherResponse

//https://openweathermap.org/weather-conditions
open class OWMWeatherResponse(

    @SerializedName("id")
    @Expose
    override var id: Int = 0,

    @SerializedName("main")
    @Expose
    override var groupOfWeatherParameters: String = "",

    @SerializedName("description")
    @Expose
    override var description: String = "",

    @SerializedName("icon")
    @Expose
    override var icon: String = ""

) : RealmObject(), IOWMWeatherResponse