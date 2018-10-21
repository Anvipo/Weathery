package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract

interface ForecastContract : WeatherContract {

    interface View<in T : IForecast> : WeatherContract.View {

        fun showWeatherResponse(data: List<T>)

        fun startWeatherInfoActivity(itemData: T)

    }

    interface Presenter<T : IForecast> : WeatherContract.Presenter<View<T>> {

        fun onItemClicked(itemData: T)

    }

}
