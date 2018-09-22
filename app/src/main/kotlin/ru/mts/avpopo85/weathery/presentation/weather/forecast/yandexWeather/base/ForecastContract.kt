package ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.base

import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract

interface ForecastContract : WeatherContract {

    interface View : WeatherContract.View {

        fun showWeatherResponse(data: List<YWForecast>)

    }

    interface Presenter : WeatherContract.Presenter<View> {

        fun loadForecast()

    }

}
