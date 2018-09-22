package ru.mts.avpopo85.weathery.domain.mapper.base

import ru.mts.avpopo85.weathery.data.model.base.CurrentWeatherResponse
import ru.mts.avpopo85.weathery.domain.model.base.CurrentWeather

interface CurrentWeatherMapper<in D : CurrentWeatherResponse, out B : CurrentWeather> {

    fun mapCurrentWeatherResponse(currentWeatherResponse: D): B

}
