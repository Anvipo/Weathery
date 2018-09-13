package ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.CurrentWeather

class YandexCurrentWeatherInteractor(
    private val weatherRepository: WeatherRepository,
    private val yandexCurrentWeatherMapper: YandexCurrentWeatherMapper
) {
    fun getCurrentWeather(): Single<CurrentWeather> = weatherRepository
        .getCurrentWeather()
        .map { yandexCurrentWeatherMapper.mapCurrentWeatherResponse(it.currentWeatherResponse) }
}