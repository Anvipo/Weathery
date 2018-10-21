package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.utils

import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.ONE_HOUR_IN_MILLIS

//TODO пользователь выбирает интервал обновления
const val OWM_CURRENT_WEATHER_CACHE_LIFETIME_IN_MS: Long =
    (1.5 * ONE_HOUR_IN_MILLIS).toLong() //= 1.5 hours

//TODO пользователь выбирает интервал обновления
const val OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS: Long = 3 * ONE_HOUR_IN_MILLIS //= 3 hours
