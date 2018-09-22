package ru.mts.avpopo85.weathery.domain.interactor.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.model.base.Forecast

interface ForecastInteractor<T : Collection<Forecast>> {

    fun getForecast(): Single<T>

}