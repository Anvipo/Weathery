package ru.mts.avpopo85.weathery.domain.weather.yandex

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.global.repositories.YandexWeatherRepository
import ru.mts.avpopo85.weathery.models.weather.yandex.CurrentWeather
import ru.mts.avpopo85.weathery.models.weather.yandex.Forecasts
import ru.mts.avpopo85.weathery.models.weather.yandex.WeatherResponse


class YandexWeatherInteractor(private val yandexWeatherRepository: YandexWeatherRepository) {

    fun getCurrentWeather(): Single<CurrentWeather> {
        return yandexWeatherRepository.getCurrentWeather()!!
    }

    fun getgetWeatherForecast(): Single<List<Forecasts>> {
        return yandexWeatherRepository.getWeatherForecast()!!
    }

    fun getWeatherResponse(): Single<WeatherResponse> {


        return yandexWeatherRepository.getWeatherResponse()
    }

    fun getWeatherResponse2(): Observable<WeatherResponse> {
        return yandexWeatherRepository.getWeatherResponse2()
    }

    fun getWeatherResponse3(): Maybe<WeatherResponse>? {
        return yandexWeatherRepository.getWeatherResponse3()
    }

    fun getWeatherResponse4(): Completable? {
        return yandexWeatherRepository.getWeatherResponse4()
    }
}