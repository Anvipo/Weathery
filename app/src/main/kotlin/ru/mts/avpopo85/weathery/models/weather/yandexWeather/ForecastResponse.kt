package ru.mts.avpopo85.weathery.models.weather.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
        //date of the forecast, in the format YYYY-MM-DD
        @SerializedName("date")
        @Expose
        val date: String,

        //the date of the forecast in Unix time
        @SerializedName("date_ts")
        @Expose
        val date_ts: Int,

        //weekNumber
        @SerializedName("week")
        @Expose
        val weekSerialNumber: Int,

        //time of the sunrise in local time (may be omitted for polar regions)
        @SerializedName("sunrise")
        @Expose
        val sunriseInLocalTime: String?,

        //time of the sunset in local time (may be omitted for polar regions)
        @SerializedName("sunset")
        @Expose
        val sunsetInLocalTime: String?,

        //code of the lunar phase
        @SerializedName("moon_code")
        @Expose
        val moonCode: Int,

        //text code for the lunar phase
        @SerializedName("moon_text")
        @Expose
        val moonText: String,

        //forecasts by time of day and 12-hour forecasts
        @SerializedName("parts")
        @Expose
        val partsResponse: PartsResponse,

        //объект почасового прогноза погоды. содержит 24 части
        @SerializedName("hours")
        @Expose
        val hours: List<HoursResponse>
)
