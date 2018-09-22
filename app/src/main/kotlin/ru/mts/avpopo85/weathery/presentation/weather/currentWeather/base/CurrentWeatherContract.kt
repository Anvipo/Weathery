package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherType

interface CurrentWeatherContract : WeatherContract {

    interface View : WeatherContract.View {

        fun showWeatherResponse(data: YWCurrentWeatherType)

    }

    interface Presenter : WeatherContract.Presenter<View> {

        fun loadCurrentWeather()

    }

}
