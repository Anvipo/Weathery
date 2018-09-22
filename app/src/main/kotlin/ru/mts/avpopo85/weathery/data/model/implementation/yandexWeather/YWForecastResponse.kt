package ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.mts.avpopo85.weathery.data.model.base.ForecastResponse

/**This object contains weather forecast data.*/
open class YWForecastResponse(

    /**Date of the forecast, in the format YYYY-MM-DD.*/
    @SerializedName("date")
    @Expose
    override var date: String = "",

    /**The date of the forecast in Unix time.*/
    @PrimaryKey
    @SerializedName("date_ts")
    @Expose
    var date_ts: Int = 0,

    /**Week number.*/
    @SerializedName("week")
    @Expose
    var weekSerialNumber: Int = 0,

    /**Time of the sunrise in local time (may be omitted for polar regions).*/
    @SerializedName("sunrise")
    @Expose
    var sunriseInLocalTime: String? = null,

    /**Time of the sunset in local time (may be omitted for polar regions).*/
    @SerializedName("sunset")
    @Expose
    var sunsetInLocalTime: String? = null,

    /**Code of the lunar phase*/
    @SerializedName("moon_code")
    @Expose
    var moonCode: Int = 0,

    /**Text code for the lunar phase*/
    @SerializedName("moon_text")
    @Expose
    var moonText: String = "",

    /**Forecasts by time of day and 12-hour forecasts*/
    @SerializedName("parts")
    @Expose
    var YWPartsResponse: YWPartsResponse? = null,

    /**Object for the hourly forecast*/
    @SerializedName("hours")
    @Expose
    var YWHours: RealmList<YWHourInfoResponse> = RealmList()

) : RealmObject(), ForecastResponse
