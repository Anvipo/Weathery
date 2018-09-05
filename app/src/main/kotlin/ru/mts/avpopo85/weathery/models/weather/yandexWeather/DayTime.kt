package ru.mts.avpopo85.weathery.models.weather.yandexWeather

data class DayTime(
    val temperatureMinimum: Double,

    val temperatureMaximum: Double,

    val temperatureAverage: Double,

    val feelsLikeTemperature: Double,

    val iconUrl: String,

    val condition: String,

    val daytime: String,

    val polar: String,

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
