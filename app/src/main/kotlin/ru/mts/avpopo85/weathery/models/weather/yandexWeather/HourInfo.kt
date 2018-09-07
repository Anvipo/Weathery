package ru.mts.avpopo85.weathery.models.weather.yandexWeather

data class HourInfo(
    val hourInLocalTime: String,

    val hourInUnixTime: String,

    val temperature: Double,

    val feelsLikeTemperature: Double,

    val iconUrl: String,

    val condition: String,

    val windSpeed: Double,

    val windGust: Double,

    val windDirection: String,

    val pressureInMM: Double,

    val pressureInPa: Double,

    val humidityInPercents: Double,

    val precipitationInMm: Double,

    val precipitationInMinutes: Double,

    val precipitationType: String,

    val precipitationStrength: String,

    val cloudness: String
)
