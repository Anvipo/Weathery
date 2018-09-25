package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMForecastSysResponse

open class OWMForecastSysResponse(

    @SerializedName("pod")
    @Expose
    override var dayTime: String = ""

) : RealmObject(), IOWMForecastSysResponse
