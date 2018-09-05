package ru.mts.avpopo85.weathery.models.weather.yandexWeather

data class Parts(
    val nightForecast: DayTime,

    val morningForecast: DayTime,

    val dayForecast: DayTime,

    val eveningForecast: DayTime,

    val _12HoursDayForecast: DayShort,

    val _12HoursNightForecast: DayShort
)
