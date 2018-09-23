package ru.mts.avpopo85.weathery.domain.interactor.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather

interface ICurrentWeatherInteractor<T : ICurrentWeather> {

    fun getCurrentWeather(): Single<T>

}