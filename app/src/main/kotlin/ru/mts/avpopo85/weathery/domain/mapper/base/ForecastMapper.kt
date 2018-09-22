package ru.mts.avpopo85.weathery.domain.mapper.base

import ru.mts.avpopo85.weathery.data.model.base.ForecastResponse
import ru.mts.avpopo85.weathery.domain.model.base.Forecast

interface ForecastMapper<in D : Collection<ForecastResponse>, out B : Collection<Forecast>> {

    fun mapForecast(forecastResponse: D): B

}
