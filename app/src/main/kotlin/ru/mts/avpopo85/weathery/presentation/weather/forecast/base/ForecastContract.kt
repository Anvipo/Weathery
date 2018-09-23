package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract

interface ForecastContract : WeatherContract {

    interface View<T : Collection<IForecast>> : WeatherContract.View {

        fun showWeatherResponse(data: T)

    }

    interface Presenter<T : Collection<IForecast>> : WeatherContract.Presenter<View<T>> {

        fun loadForecast()

    }

}
