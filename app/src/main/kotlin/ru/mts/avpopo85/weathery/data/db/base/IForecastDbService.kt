package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType

interface IForecastDbService<T : Collection<YWForecastResponseType>> {

    fun saveForecastResponse(forecastResponseList: T): Single<T>

    fun getForecastResponse(isConnectedToInternet: Boolean = true): Single<T>

}