package ru.mts.avpopo85.weathery.models.weather.yandexWeather

data class DayShort(
    val temperature: Double,

    val feelsLikeTemperature: Double,

    val iconUrl: String,

    val condition: String,

    val windSpeed: Double,

    val windGust: Double,

    val windDirection: String,

    val pressureInMM: Double,

    val pressureInPa: String,

    val humidityInPercents: Double,

    val precipitationType: String,

    val precipitationStrength: String,

    val cloudness: String
)
