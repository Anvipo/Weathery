package ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.currentWeather.IOWMCurrentWeatherRealmResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMCloudsResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMCoordinatesResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMWeatherResponse

open class OWMCurrentWeatherResponse(

    @SerializedName("coord")
    @Expose
    override var coordinates: OWMCoordinatesResponse? = null,

    @SerializedName("weather")
    @Expose
    override var weather: RealmList<OWMWeatherResponse> = RealmList(),

    @SerializedName("base")
    @Expose
    override var base: String = "",

    @SerializedName("main")
    @Expose
    override var main: OWMCurrentWeatherMainResponse? = null,

    @SerializedName("visibility")
    @Expose
    override var visibility: Int = 0,

    @SerializedName("wind")
    @Expose
    override var wind: OWMWindResponse? = null,

    @SerializedName("clouds")
    @Expose
    override var clouds: OWMCloudsResponse? = null,

    @PrimaryKey
    @SerializedName("dt")
    @Expose
    override var timeOfDataCalculationUnixUTCInSeconds: Long = 0,

    @SerializedName("sys")
    @Expose
    override var sys: OWMSysResponse? = null,

    @SerializedName("id")
    @Expose
    override var cityID: Int = 0,

    @SerializedName("name")
    @Expose
    override var cityName: String = "",

    @SerializedName("cod")
    @Expose
    override var code: Int = 0

) : RealmObject(), IOWMCurrentWeatherRealmResponse