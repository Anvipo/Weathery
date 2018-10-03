package ru.mts.avpopo85.weathery.data.db.implementation.realm.openWeatherMap

//TODO пользователь выбирает интервал обновления
const val ONE_HOUR_IN_MILLIS: Long = 3_600_000

const val OWM_CURRENT_WEATHER_CACHE_LIFETIME_IN_MS: Long = ONE_HOUR_IN_MILLIS //= 1 hours

const val OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS: Long = 3 * ONE_HOUR_IN_MILLIS //= 3 hours
