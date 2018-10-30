package ru.mts.avpopo85.weathery.domain.mapper.base

import ru.mts.avpopo85.weathery.data.model.base.common.IForecastResponse
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast

interface IForecastMapper<D : Collection<IForecastResponse>, B : Collection<IForecast>> {

    fun mapForecast(forecastListResponse: D): B

}
