package ru.mts.avpopo85.weathery.domain.mapper.base

import ru.mts.avpopo85.weathery.data.model.base.common.IForecastResponse
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast

//TODO Почему если здесь указать in и out,
//то Dagger будет ругаться (не видит provide реализации?)
interface IForecastMapper<D : Collection<IForecastResponse>, B : Collection<IForecast>> {

    fun mapForecast(forecastListResponse: D): B

}
