package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWCurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract

interface CurrentWeatherContract : WeatherContract {

    interface View : WeatherContract.View {

        fun showWeatherResponse(data: YWCurrentWeather)

    }

    interface Presenter : WeatherContract.Presenter<View> {

        fun loadCurrentWeather()

    }

}
