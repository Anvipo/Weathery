package ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather.YandexCurrentWeatherMapper.mapCurrentWeatherResponse
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.CurrentWeather

class YandexCurrentWeatherInteractor(private val weatherRepository: WeatherRepository) {
    fun getCurrentWeather(): Single<CurrentWeather> =
        weatherRepository
            .getCurrentWeather()
            .map { mapCurrentWeatherResponse(it.currentWeatherResponse) }
}