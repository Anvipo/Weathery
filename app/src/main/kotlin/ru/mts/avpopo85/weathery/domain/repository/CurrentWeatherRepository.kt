package ru.mts.avpopo85.weathery.domain.repository

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.CurrentWeatherResponse

interface CurrentWeatherRepository<T : CurrentWeatherResponse> {

    fun getCurrentWeather(): Single<T>

}
