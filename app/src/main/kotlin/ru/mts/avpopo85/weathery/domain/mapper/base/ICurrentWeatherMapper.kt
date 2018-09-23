package ru.mts.avpopo85.weathery.domain.mapper.base

import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherResponse
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather

//TODO Почему если здесь указать in и out,
//то Dagger будет ругаться (не видит provide реализации?
interface ICurrentWeatherMapper<D : ICurrentWeatherResponse, B : ICurrentWeather> {

    fun mapCurrentWeatherResponse(currentWeatherResponse: D): B

}
