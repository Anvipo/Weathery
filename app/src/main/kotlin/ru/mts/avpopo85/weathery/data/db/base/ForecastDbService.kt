package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.utils.ForecastResponseType

interface ForecastDbService<T : Collection<ForecastResponseType>> {

    fun saveForecastResponse(forecastListResponse: T): Single<T>

    fun getForecastResponse(isConnectedToInternet: Boolean = true): Single<T>

}