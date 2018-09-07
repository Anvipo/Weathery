package ru.mts.avpopo85.weathery.models.weather.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
        /**
         * Date of the forecast, in the format YYYY-MM-DD
         */
        @SerializedName("date")
        @Expose
        val date: String,

        /**
         * The date of the forecast in Unix time
         * */
        @SerializedName("date_ts")
        @Expose
        val date_ts: Int,

        /**
         * Week number
         */
        @SerializedName("week")
        @Expose
        val weekSerialNumber: Int,

        /**
         * Time of the sunrise in local time (may be omitted for polar regions)
         */
        @SerializedName("sunrise")
        @Expose
        val sunriseInLocalTime: String?,

        /**
         * Time of the sunset in local time (may be omitted for polar regions)
         */
        @SerializedName("sunset")
        @Expose
        val sunsetInLocalTime: String?,

        /**
         * Code of the lunar phase
         */
        @SerializedName("moon_code")
        @Expose
        val moonCode: Int,

        /**
         * Text code for the lunar phase
         */
        @SerializedName("moon_text")
        @Expose
        val moonText: String,

        /**
         * Forecasts by time of day and 12-hour forecasts
         */
        @SerializedName("parts")
        @Expose
        val partsResponse: PartsResponse,

        /**
         * Object for the hourly forecast
         */
        @SerializedName("hours")
        @Expose
        val hours: List<HoursResponse>?
)
