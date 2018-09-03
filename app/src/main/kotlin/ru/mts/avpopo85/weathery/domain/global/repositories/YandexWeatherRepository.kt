package ru.mts.avpopo85.weathery.domain.global.repositories

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.models.weather.yandex.CurrentWeather
import ru.mts.avpopo85.weathery.models.weather.yandex.Forecasts
import ru.mts.avpopo85.weathery.models.weather.yandex.WeatherResponse

interface YandexWeatherRepository {
    fun getWeatherResponse(): Single<WeatherResponse>
    fun getCurrentWeather(): Single<CurrentWeather>?
    fun getWeatherForecast(): Single<List<Forecasts>>?

    fun getWeatherResponse2(): Observable<WeatherResponse>
    fun getWeatherResponse3(): Maybe<WeatherResponse>
    fun getWeatherResponse4(): Completable
}