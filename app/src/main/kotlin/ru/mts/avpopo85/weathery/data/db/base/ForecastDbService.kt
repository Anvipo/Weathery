package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.ForecastResponse

interface ForecastDbService<T : Collection<ForecastResponse>> {

    fun saveForecastResponse(currentWeatherResponse: T): Single<T>

    fun getForecastResponse(isConnectedToInternet: Boolean = true): Single<T>

}