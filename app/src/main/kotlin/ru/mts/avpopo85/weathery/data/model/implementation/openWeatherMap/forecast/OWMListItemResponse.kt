package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IOWMListItemResponse
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMCloudsResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather.OWMWindResponse

open class OWMListItemResponse(

    override var cityName: String = "",

    override var saveUnixTime: Long = 0,

    override var coordinates: GeographicCoordinates? = null,

    @PrimaryKey
    @SerializedName("dt")
    @Expose
    override var timeOfDataCalculationUnixUTCInSeconds: Long = 0,

    @SerializedName("rain")
    @Expose
    override var rain: OWMRainResponse? = null,

    @SerializedName("dt_txt")
    @Expose
    override var dateUTC: String = "",

    @SerializedName("weather")
    @Expose
    override var weather: RealmList<OWMWeatherResponse> = RealmList(),

    @SerializedName("main")
    @Expose
    override var main: OWMForecastMainResponse? = null,

    @SerializedName("clouds")
    @Expose
    override var clouds: OWMCloudsResponse? = null,

    @SerializedName("sys")
    @Expose
    override var sys: OWMForecastSysResponse? = null,

    @SerializedName("wind")
    @Expose
    override var wind: OWMWindResponse? = null

) : RealmObject(), IOWMListItemResponse
