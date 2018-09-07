package ru.mts.avpopo85.weathery.domain.global.repositories

import io.reactivex.Single
import retrofit2.Call
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.CurrentWeatherResponse
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.ForecastResponse
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.WeatherResponse

interface WeatherRepository {
    fun getForecast(): Single<WeatherResponse>

    fun getCurrentWeather(): Single<WeatherResponse>
}