package ru.mts.avpopo85.weathery.data.repositories

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.CurrentWeatherResponse
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.ForecastResponse
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.WeatherResponse
import ru.mts.avpopo85.weathery.utils.YandexWeatherLanguages

class YandexWeatherRepository(private val yandexWeatherApiService: YandexWeatherApiService) :
    WeatherRepository {
    override fun getForecast(): Single<WeatherResponse> =
        yandexWeatherApiService.forecast(
            45.03,
            38.98,
            YandexWeatherLanguages.RU_RU.lang_code,
            11,
            true,
            true
        )

    override fun getCurrentWeather(): Single<WeatherResponse> =
        yandexWeatherApiService.currentWeather(
            45.03,
            38.98,
            YandexWeatherLanguages.RU_RU.lang_code
        )
}
