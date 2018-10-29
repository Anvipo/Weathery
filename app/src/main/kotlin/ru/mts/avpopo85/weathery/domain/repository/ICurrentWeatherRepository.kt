package ru.mts.avpopo85.weathery.domain.repository

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherResponse

interface ICurrentWeatherRepository<T : ICurrentWeatherResponse> {

    fun getCurrentWeather(): Single<T>

}
