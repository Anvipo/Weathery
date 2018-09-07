package ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast.YandexForecastMapper.mapForecast
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.Forecast

class YandexForecastInteractor(private val weatherRepository: WeatherRepository) {
    fun getForecast(): Single<List<Forecast>> =
        weatherRepository
            .getForecast()
            .map { mapForecast(it.forecasts) }
}