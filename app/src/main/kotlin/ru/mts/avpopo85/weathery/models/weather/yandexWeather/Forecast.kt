package ru.mts.avpopo85.weathery.models.weather.yandexWeather

data class Forecast(
    val date: String,

    val date_ts: String,

    val weekSerialNumber: Int,

    val sunriseInLocalTime: String?,

    val sunsetInLocalTime: String?,

    val moonCode: String,

    val moonText: String,

    val parts: Parts,

    val hours: List<Hours>
)
