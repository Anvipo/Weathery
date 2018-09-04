package ru.mts.avpopo85.weathery.models.weather.yandexWeather

data class CurrentWeather(
        val temperature: Int,

        val feelsLikeTemperature: Int,

        val waterTemperature: String,

        val iconUrl: String,

        val weatherDescription: String,

        val windSpeed: Int,

        val windGust: Int,

        val windDirection: String,

        val pressureInMmHg: Int,

        val pressureInhHpa: Int,

        val humidityInPercents: Int,

        val daytime: String,

        val polar: String,

        val season: String,

        val observationUnixTime: String,

        val precipitationType: String,

        val precipitationStrength: String,

        val cloudness: String
)