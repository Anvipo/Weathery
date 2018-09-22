package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract
import ru.mts.avpopo85.weathery.utils.CurrentWeatherType

interface CurrentWeatherContract : WeatherContract {

    interface View : WeatherContract.View {

        fun showWeatherResponse(data: CurrentWeatherType)

    }

    interface Presenter : WeatherContract.Presenter<View> {

        fun loadCurrentWeather()

    }

}
