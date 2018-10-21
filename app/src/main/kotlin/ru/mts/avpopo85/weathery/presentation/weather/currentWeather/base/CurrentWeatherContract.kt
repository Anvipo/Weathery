package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract

interface CurrentWeatherContract : WeatherContract {

    interface View<in T : ICurrentWeather> : WeatherContract.View {

        fun showWeatherResponse(data: T)

    }

    interface Presenter<T : ICurrentWeather> : WeatherContract.Presenter<View<T>>

}
