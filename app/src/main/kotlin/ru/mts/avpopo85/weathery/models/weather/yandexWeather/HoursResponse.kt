package ru.mts.avpopo85.weathery.models.weather.yandexWeather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HoursResponse(
        //Значение часа, для которого даётся прогноз (0-23), локальное время
        @SerializedName("hour")
        @Expose
        val hourInLocalTime: String,

        //время прогноза в Unixtime
        @SerializedName("hour_ts")
        @Expose
        val hourInUnixTime: Int,

        //температура (°С)
        @SerializedName("temp")
        @Expose
        val temperature: Double,

        //ощущаемая температура (°С)
        @SerializedName("feels_like")
        @Expose
        val feelsLikeTemperature: Double,

        //код иконки погоды
        //иконка доступна по адресу https://yastatic/weather/i/icons/blueye/color/svg/
        // /<значение из поля icon>.svg
        @SerializedName("icon")
        @Expose
        val iconId: String,

        //код расшифровки погодного описания
        @SerializedName("condition")
        @Expose
        val condition: String,

        //скорость ветра (в м/с)
        @SerializedName("wind_speed")
        @Expose
        val windSpeed: Double,

        //скорость порывов ветра (в м/с)
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
        val pressureInPa: Double,

        //влажность воздуха (в процентах)
        @SerializedName("humidity")
        @Expose
        val humidityInPercents: Double,

        //прогнозируемое количество осадков (в мм)
        @SerializedName("prec_mm")
        @Expose
        val precipitationInMm: Double,

        //прогнозируемый период осадков (в минутах)
        @SerializedName("prec_period")
        @Expose
        val precipitationInMinutes: Double,

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
        val cloudness: Double
)
