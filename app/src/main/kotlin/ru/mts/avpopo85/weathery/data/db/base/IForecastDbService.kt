package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastResponse

interface IForecastDbService<T : IForecastResponse> {

    fun saveForecastResponse(forecastResponseList: List<T>): Single<List<T>>

    fun getForecastResponse(isConnectedToInternet: Boolean = true): Single<List<T>>

}