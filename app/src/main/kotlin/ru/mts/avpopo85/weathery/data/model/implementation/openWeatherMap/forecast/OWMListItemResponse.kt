package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMListItemResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMCloudsResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather.OWMWindResponse

@Suppress("SpellCheckingInspection")
data class OWMListItemResponse(

    @SerializedName("dt")
    @Expose
    override val timeOfDataForecastedUnixUTC: Double,

    @SerializedName("rain")
    @Expose
    override val rain: OWMRainResponse?,

    @SerializedName("dt_txt")
    @Expose
    override val timeOfCalculationUTC: String,

    @SerializedName("weather")
    @Expose
    override val weather: List<OWMWeatherResponse>,

    @SerializedName("main")
    @Expose
    override val main: OWMForecastMainResponse,

    @SerializedName("clouds")
    @Expose
    override val clouds: OWMCloudsResponse,

    @SerializedName("sys")
    @Expose
    override val sys: OWMForecastSysResponse,

    @SerializedName("wind")
    @Expose
    override val wind: OWMWindResponse

) : IOWMListItemResponse
