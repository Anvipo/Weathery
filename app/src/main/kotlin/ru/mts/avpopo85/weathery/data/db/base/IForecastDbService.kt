package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse

interface IForecastDbService<T : IForecastRealmResponse> {

    fun saveForecastResponse(forecastResponseList: List<T>): Single<List<T>>

    fun getForecastResponse(isConnectedToInternet: Boolean): Single<List<T>>

}