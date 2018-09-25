package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMRainResponse

open class OWMRainResponse(

    @SerializedName("3h")
    @Expose
    override var rainVolumeForLast3hoursMm: Double = 0.0

) : RealmObject(), IOWMRainResponse
