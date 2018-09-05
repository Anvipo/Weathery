package ru.mts.avpopo85.weathery.models.weather.yandexWeather

data class CurrentWeather(
        val temperature: Double,

        val feelsLikeTemperature: Double,

        val waterTemperature: String,

        val iconUrl: String,

        val weatherDescription: String,

        val windSpeed: Double,

        val windGust: Double,

        val windDirection: String,

        val pressureInMmHg: Double,

        val pressureInhHpa: Double,

        val humidityInPercents: Double,

        val daytime: String,

        val polar: String,

        val season: String,

        val observationUnixTime: String,

        val precipitationType: String,

        val precipitationStrength: String,

        val cloudness: String
)