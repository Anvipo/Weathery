package ru.mts.avpopo85.weathery.domain.interactor.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast

interface IForecastInteractor<T : Collection<IForecast>> {

    fun getForecast(): Single<T>

}
