package ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.domain.models.CurrentWeather
import javax.inject.Inject

class YandexCurrentWeatherInteractor
@Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val yandexCurrentWeatherMapper: YandexCurrentWeatherMapper
) {

    fun getCurrentWeather(): Single<CurrentWeather> = weatherRepository
        .getCurrentWeather()
        .map { yandexCurrentWeatherMapper.mapCurrentWeatherResponse(it.currentWeatherResponse) }

}