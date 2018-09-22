package ru.mts.avpopo85.weathery.domain.interactor.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.model.base.CurrentWeather

interface CurrentWeatherInteractor<T : CurrentWeather> {

    fun getCurrentWeather(): Single<T>

}