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
    override val numberOfLines: Int,

    @SerializedName("cod")
    @Expose
    override val code: String,

    @SerializedName("message")
    @Expose
    override val message: Double,

    @SerializedName("list")
    @Expose
    override val forecastsList: List<OWMListItemResponse>

) : IOWMForecastResponse
