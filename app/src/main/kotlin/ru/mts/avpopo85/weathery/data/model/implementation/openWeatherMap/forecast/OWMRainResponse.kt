package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMRainResponse

data class OWMRainResponse(

    @SerializedName("3h")
    @Expose
    override val rainVolumeForLast3hoursMm: Double

) : IOWMRainResponse
