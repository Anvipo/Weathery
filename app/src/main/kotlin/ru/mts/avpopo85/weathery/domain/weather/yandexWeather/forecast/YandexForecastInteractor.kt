package ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.domain.models.Forecast
import javax.inject.Inject

class YandexForecastInteractor
@Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val yandexForecastMapper: YandexForecastMapper
) {

    fun getForecast(): Single<List<Forecast>> =
        weatherRepository
            .getForecast()
            .map { yandexForecastMapper.mapForecast(it.forecasts) }

}