package ru.mts.avpopo85.weathery.domain.repository

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastResponse

interface IForecastRepository<T : Collection<IForecastResponse>> {

    fun getForecast(): Single<T>

}
