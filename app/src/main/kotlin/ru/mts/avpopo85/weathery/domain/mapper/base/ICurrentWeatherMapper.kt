package ru.mts.avpopo85.weathery.domain.mapper.base

import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherResponse
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather

interface ICurrentWeatherMapper<D : ICurrentWeatherResponse, B : ICurrentWeather> {

    fun mapCurrentWeatherResponse(currentWeatherResponseData: D): B

}
