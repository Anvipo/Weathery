package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.currentWeather

import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWCurrentWeather

/**This object contains information about the current weather.*/
data class YWCurrentWeather(

    override val isFresh: Boolean,

    override val temperature: String,

    override val feelsLikeTemperature: String,

    override val waterTemperature: String,

    override val iconUrl: String,

    override val weatherDescription: String,

    override val windSpeed: String,

    override val windGustsSpeed: String,

    override val windDirection: String,

    override val atmosphericPressureInMmHg: String,

    override val atmosphericPressureInhPa: String,

    override val humidity: String,

    override val daytime: String,

    override val polar: String,

    override val season: String,

    override val precipitationType: String,

    override val precipitationStrength: String,

    override val cloudiness: String,

    override val cityName: String,

    override val timeOfDataCalculation: String

) : IYWCurrentWeather