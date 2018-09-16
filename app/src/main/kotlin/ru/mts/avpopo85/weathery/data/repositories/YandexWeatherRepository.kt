package ru.mts.avpopo85.weathery.data.repositories

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.network.NetManager
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.data.CityInfoResponse
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.data.CurrentWeatherResponse
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.data.TimeZoneInfo
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.data.WeatherResponse
import ru.mts.avpopo85.weathery.utils.YandexWeatherLanguages

class YandexWeatherRepository(
    private val yandexWeatherApiService: YandexWeatherApiService,
    private val netManager: NetManager
) : WeatherRepository {
    private val weatherResponseMock = WeatherResponse(
        0,
        "",
        CityInfoResponse(
            0.0,
            0.0,
            TimeZoneInfo(0.0, "", "", true),
            0.0,
            0.0,
            ""
        ),
        CurrentWeatherResponse(
            0.0,
            0.0,
            0.0,
            "",
            "",
            0.0,
            0.0,
            "",
            0.0,
            0.0,
            0.0,
            "",
            true,
            "",
            0,
            0,
            0.0,
            0.0
        ),
        listOf()
    )

    override fun getForecast(): Single<WeatherResponse> =
        if (netManager.isConnectedToInternet) {
            yandexWeatherApiService.forecast(
                45.03,
                38.98,
                YandexWeatherLanguages.RU_RU.lang_code,
                11,
                true,
                true
            )
        } else {
            Single.just(weatherResponseMock)
        }

    override fun getCurrentWeather(): Single<WeatherResponse> =
        if (netManager.isConnectedToInternet) {
            yandexWeatherApiService.currentWeather(
                45.03,
                38.98,
                YandexWeatherLanguages.RU_RU.lang_code
            )
        } else {
            Single.just(weatherResponseMock)
        }
}
