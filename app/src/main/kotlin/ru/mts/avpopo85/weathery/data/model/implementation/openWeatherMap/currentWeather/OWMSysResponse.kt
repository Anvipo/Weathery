package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.currentWeather.IOWMSysResponse

open class OWMSysResponse(

    @SerializedName("type")
    @Expose
    override var type: Int = 0,

    @SerializedName("id")
    @Expose
    override var id: Int = 0,

    @SerializedName("message")
    @Expose
    override var message: Double = 0.0,

    @SerializedName("country")
    @Expose
    override var countryCode: String = "",

    @SerializedName("sunrise")
    @Expose
    override var sunrise: Int = 0,

    @SerializedName("sunset")
    @Expose
    override var sunset: Int = 0

) : RealmObject(),
    IOWMSysResponse