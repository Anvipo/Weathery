package ru.mts.avpopo85.weathery.domain.weather.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.global.repositories.YandexWeatherRepository
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexCurrentWeatherMapper.getCurrentInfoList
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexCurrentWeatherMapper.mapCurrentWeatherResponse
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexForecastMapper.getForecastInfoList
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexForecastMapper.mapForecast

class YandexWeatherInteractor(private val yandexWeatherRepository: YandexWeatherRepository) {
    fun getCurrentWeather(): Single<List<String>> {
        return yandexWeatherRepository.getCurrentWeather().map {
            val currentWeatherResponse = it.currentWeatherResponse

            val mappedInfo = mapCurrentWeatherResponse(currentWeatherResponse)

            getCurrentInfoList(mappedInfo)
        }
    }

    fun getForecast(): Single<List<String>> {
        return yandexWeatherRepository.getForecast().map {
            val forecast = it.forecasts

            val mappedInfo = mapForecast(forecast)

            getForecastInfoList(mappedInfo)
        }
    }
}