package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMForecastResponse

data class OWMForecastResponse(

    @SerializedName("city")
    @Expose
    override val city: OWMCityResponse,

    @SerializedName("cnt")
    @Expose
    override val numberOfLines: Double,

    @SerializedName("cod")
    @Expose
    override val code: String,

    @SerializedName("message")
    @Expose
    override val message: Double,

    @SerializedName("list")
    @Expose
    override val list: List<OWMListItemResponse>

) : IOWMForecastResponse
