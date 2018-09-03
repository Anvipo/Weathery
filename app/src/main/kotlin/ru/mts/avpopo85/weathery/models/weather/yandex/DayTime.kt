package ru.mts.avpopo85.weathery.models.weather.yandex

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DayTime(
        //минимальная температура для времени суток (°С)
        @SerializedName("temp_min")
        @Expose
        val temperatureMinimum: Double,

        //максимальная температура для времени суток (°С)
        @SerializedName("temp_max")
        @Expose
        val temperatureMaximum: Double,

        //средняя температура для времени суток (°С)
        @SerializedName("temp_avg")
        @Expose
        val temperatureAverage: Double,

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

        //светлое или тёмное время суток
        @SerializedName("daytime")
        @Expose
        val daytime: String,

        //признак полярного дня или ночи
        @SerializedName("polar")
        @Expose
        val polar: Boolean,

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
        val cloudness: Double
)
