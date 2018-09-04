package ru.mts.avpopo85.weathery.models.weather.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Forecasts(
        //date of the forecast, in the format YYYY-MM-DD
        @SerializedName("date")
        @Expose
        val date: String,

        //the date of the forecast in Unix time
        @SerializedName("date_ts")
        @Expose
        val date_ts: Double,

        //weekNumber
        @SerializedName("week")
        @Expose
        val weekSerialNumber: Double,

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
        val parts: Parts,

        //object with the weather forecast for the night

        //ATTENTION. The beginning of the nighttime period corresponds to the beggining of the 24-hour period
        //To specify the upcoming night temperatures, use the object for
        //the nighttime forecast for the next day
        @SerializedName("night")
        @Expose
        val night: DayTime,

        //minimum temperature for the time of day (°С)
        @SerializedName("temp_min")
        @Expose
        val minimumTemperature: Double,

        //maximum temperature for the time of day (°С)
        @SerializedName("temp_max")
        @Expose
        val maximumTemperature: Double,

        //average temperature for the time of day (°С)
        @SerializedName("temp_avg")
        @Expose
        val averageTemperature: Double,

        //what the temperature feels like (°С)
        @SerializedName("feels_like")
        @Expose
        val feelsLikeTemperature: Double,

        //the code of the weather icon
        //the icon is available at https://yastatic/weather/i/icons/blueye/color/svg/
        // /<value from the icon field>.svg
        @SerializedName("icon")
        @Expose
        val iconId: String,

        //the code for the weather description
        @SerializedName("condition")
        @Expose
        val condition: String,

        //light or dark time of the day
        @SerializedName("daytime")
        @Expose
        val daytime: String,

        //polar day or polar night
        @SerializedName("polar")
        @Expose
        val polar: Boolean,

        //wind speed (meters per second)
        @SerializedName("wind_speed")
        @Expose
        val windSpeed: Double,

        //speed of wind gusts (meters per second)
        @SerializedName("wind_gust")
        @Expose
        val windGust: Double,

        //направление ветра
        @SerializedName("wind_dir")
        @Expose
        val windDirection: String,

        //давление (в мм рт. ст.)
        @SerializedName("pressure_mm")
        @Expose
        val pressureInMM: Double,

        //давление (в гектопаскалях)
        @SerializedName("pressure_pa")
        @Expose
        val pressureInPa: String,

        //влажность воздуха (в процентах)
        @SerializedName("humidity")
        @Expose
        val humidityInPercents: Double,

        //прогнозируемое количество осадков (в мм)
        @SerializedName("prec_mm")
        @Expose
        val precipitationInMm: Double,

        //прогнозируемый период осадков (в мм)
        @SerializedName("prec_period")
        @Expose
        val precipitationPeriod: Double,

        //тип осадков
        @SerializedName("prec_type")
        @Expose
        val precipitationType: Int,

        //сила осадков
        @SerializedName("prec_strength")
        @Expose
        val precipitationStrength: Double,

        //облачность
        @SerializedName("cloudness")
        @Expose
        val cloudness: Double,

        //объект с 12-часовым прогнозом на день
        @SerializedName("day_short")
        @Expose
        val _12HoursForecast: DayShort,

        //максимальная дневная или минимальная ночная температура (°С)
        @SerializedName("temp")
        @Expose
        val temperature: Double,

        //объект почасового прогноза погоды. содержит 24 части
        @SerializedName("hours")
        @Expose
        val hours: List<Hours>,

        //Значение часа, для которого даётся прогноз (0-23), локальное время
        @SerializedName("hour")
        @Expose
        val hourInLocalTime: String,

        //время прогноза в Unixtime
        @SerializedName("hour_ts")
        @Expose
        val hourInUnixTime: Double
)
