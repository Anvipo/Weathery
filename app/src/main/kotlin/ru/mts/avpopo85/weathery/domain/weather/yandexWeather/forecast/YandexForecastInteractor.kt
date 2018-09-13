package ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.Forecast

class YandexForecastInteractor(
    private val weatherRepository: WeatherRepository,
    private val yandexForecastMapper: YandexForecastMapper
) {
    fun getForecast(): Single<List<Forecast>> =
        weatherRepository
            .getForecast()
            .map { yandexForecastMapper.mapForecast(it.forecasts) }
}