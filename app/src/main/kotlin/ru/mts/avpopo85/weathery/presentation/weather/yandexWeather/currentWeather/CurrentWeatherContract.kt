package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.CurrentWeather
import ru.mts.avpopo85.weathery.presentation.base.BaseContract
import ru.mts.avpopo85.weathery.presentation.weather.WeatherContract

interface CurrentWeatherContract : WeatherContract {
    interface CurrentWeatherView : WeatherContract.WeatherView {
        fun showWeatherResponse(data: CurrentWeather)
    }

    interface CurrentWeatherPresenter : BaseContract.Presenter<CurrentWeatherView> {
        fun loadData()
    }
}