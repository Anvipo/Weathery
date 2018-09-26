package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.forecast

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast.IYWForecastResponse

/**This object contains weather forecast data.*/
@Suppress("SpellCheckingInspection")
open class YWForecastResponse(

    override var saveUnixTime: Long = 0,

    @SerializedName("date")
    @Expose
    override var dateUTC: String = "",

    @PrimaryKey
    @SerializedName("date_ts")
    @Expose
    override var dateInUnixtimeUTC: Long = 0,

    @SerializedName("week")
    @Expose
    override var weekSerialNumber: Int = 0,

    @SerializedName("sunrise")
    @Expose
    override var sunriseInLocalTime: String? = null,

    @SerializedName("sunset")
    @Expose
    override var sunsetInLocalTime: String? = null,

    @SerializedName("moon_code")
    @Expose
    override var moonCode: Int = 0,

    @SerializedName("moon_text")
    @Expose
    override var moonText: String = "",

    @SerializedName("parts")
    @Expose
    override var partsResponse: YWPartsResponse? = null,

    @SerializedName("hours")
    @Expose
    override var hours: RealmList<YWHourInfoResponse> = RealmList()

) : RealmObject(),
    IYWForecastResponse
