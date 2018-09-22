package ru.mts.avpopo85.weathery.data.network.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.WeatherResponse

interface ForecastApiService<WR : WeatherResponse, ForecastParameters> {

    fun getForecast(parameters: ForecastParameters): Single<WR>

}
