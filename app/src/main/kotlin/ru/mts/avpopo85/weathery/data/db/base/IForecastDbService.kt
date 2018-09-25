package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastResponse

interface IForecastDbService<T : Collection<IForecastResponse>> {

    fun saveForecastResponse(forecastResponseList: T): Single<T>

    fun getForecastResponse(isConnectedToInternet: Boolean = true): Single<T>

}