package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMForecastSysResponse

data class OWMForecastSysResponse(

    @SerializedName("pod")
    @Expose
    override val dayTime: String

) : IOWMForecastSysResponse
