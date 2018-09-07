package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import ru.mts.avpopo85.weathery.models.weather.yandexWeather.Forecast
import ru.mts.avpopo85.weathery.presentation.base.BaseContract
import ru.mts.avpopo85.weathery.presentation.weather.WeatherContract

interface ForecastContract : WeatherContract {
    interface ForecastView : WeatherContract.WeatherView {
        fun showWeatherResponse(data: List<Forecast>)
    }

    interface ForecastPresenter : BaseContract.Presenter<ForecastContract.ForecastView> {
        fun loadData()
    }
}