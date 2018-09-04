package ru.mts.avpopo85.weathery.domain.weather.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.global.repositories.YandexWeatherRepository
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherInteractorHelper.getInfoList
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherInteractorHelper.mapCurrentWeather
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.Forecasts

class YandexWeatherInteractor(private val yandexWeatherRepository: YandexWeatherRepository) {
    fun getCurrentWeather(): Single<List<String>> {
        return yandexWeatherRepository.getCurrentWeather().map {
            val currentWeatherResponse = it.currentWeatherResponse

            val mappedInfo = mapCurrentWeather(currentWeatherResponse)

            getInfoList(mappedInfo)
        }
    }

    fun getForecast(): Single<List<Forecasts>> {
        return yandexWeatherRepository.getForecast().map { it.forecasts }
    }
}