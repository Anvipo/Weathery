package ru.mts.avpopo85.weathery.domain.mapper.base

import ru.mts.avpopo85.weathery.data.model.base.ForecastResponse
import ru.mts.avpopo85.weathery.domain.model.base.Forecast

//TODO почему если здесь указать in и out Dagger будет ругаться?
interface ForecastMapper<D : Collection<ForecastResponse>, B : Collection<Forecast>> {

    fun mapForecast(forecastResponse: D): B

}
