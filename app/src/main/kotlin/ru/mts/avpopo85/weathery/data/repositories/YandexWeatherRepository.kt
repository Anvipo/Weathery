package ru.mts.avpopo85.weathery.data.repositories

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.models.CityInfoResponse
import ru.mts.avpopo85.weathery.data.models.CurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.models.TimeZoneInfo
import ru.mts.avpopo85.weathery.data.models.WeatherResponse
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService
import ru.mts.avpopo85.weathery.utils.YandexWeatherLanguages
import javax.inject.Inject

class YandexWeatherRepository
@Inject constructor(
    private val yandexWeatherApiService: YandexWeatherApiService,
    private val networkManager: NetworkManager
) : WeatherRepository {

    private val weatherResponseMock = WeatherResponse(
        0, "", CityInfoResponse(
            0.0,
            0.0,
            TimeZoneInfo(0.0, "", "", true),
            0.0,
            0.0,
            ""
        ), CurrentWeatherResponse(
            0.0, 0.0, 0.0, "", "", 0.0, 0.0, "", 0.0, 0.0, 0.0, "", true, "", 0, 0, 0.0, 0.0
        ), listOf()
    )

    override fun getForecast(): Single<WeatherResponse> =
        if (networkManager.isConnectedToInternet) {
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
        if (networkManager.isConnectedToInternet) {
            val data = yandexWeatherApiService.currentWeather(
                45.03,
                38.98,
                YandexWeatherLanguages.RU_RU.lang_code
            )

            data
        } else {
            Single.just(weatherResponseMock)
        }

}
