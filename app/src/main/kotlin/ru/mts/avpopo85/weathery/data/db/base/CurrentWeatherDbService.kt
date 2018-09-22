package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.CurrentWeatherResponse

interface CurrentWeatherDbService<T : CurrentWeatherResponse> {

    fun saveCurrentWeatherResponse(currentWeatherResponse: T): Single<T>

    fun getCurrentWeatherResponse(isConnectedToInternet: Boolean = true): Single<T>

}