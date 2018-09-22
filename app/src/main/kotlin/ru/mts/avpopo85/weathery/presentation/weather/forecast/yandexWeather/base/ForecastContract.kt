package ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.base

import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract
import ru.mts.avpopo85.weathery.utils.ForecastListType

interface ForecastContract : WeatherContract {

    interface View : WeatherContract.View {

        fun showWeatherResponse(data: ForecastListType)

    }

    interface Presenter : WeatherContract.Presenter<View> {

        fun loadForecast()

    }

}
