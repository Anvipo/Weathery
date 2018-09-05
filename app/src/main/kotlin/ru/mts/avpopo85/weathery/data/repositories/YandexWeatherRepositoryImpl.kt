package ru.mts.avpopo85.weathery.data.repositories

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService
import ru.mts.avpopo85.weathery.domain.global.repositories.YandexWeatherRepository
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.WeatherResponse
import ru.mts.avpopo85.weathery.utils.YandexWeatherLanguages

class YandexWeatherRepositoryImpl(private val yandexWeatherApiService: YandexWeatherApiService) : YandexWeatherRepository {
    override fun getForecast(): Single<WeatherResponse> =
            yandexWeatherApiService.forecast(
                    45.03,
                    38.98,
                    YandexWeatherLanguages.RU_RU.lang_code,
                    7,
                    true,
                    true)

    override fun getCurrentWeather(): Single<WeatherResponse> =
            yandexWeatherApiService.forecast(
                    45.03,
                    38.98,
                    YandexWeatherLanguages.RU_RU.lang_code,
                    0,
                    false,
                    false)
}
