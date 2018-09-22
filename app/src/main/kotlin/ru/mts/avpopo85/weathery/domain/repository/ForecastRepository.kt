package ru.mts.avpopo85.weathery.domain.repository

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.ForecastResponse

interface ForecastRepository<T : Collection<ForecastResponse>> {

    fun getForecast(): Single<T>

}
