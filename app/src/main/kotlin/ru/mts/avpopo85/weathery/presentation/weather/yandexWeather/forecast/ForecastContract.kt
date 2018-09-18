package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import ru.mts.avpopo85.weathery.domain.models.Forecast
import ru.mts.avpopo85.weathery.presentation.weather.WeatherContract

interface ForecastContract : WeatherContract {

    interface View : WeatherContract.View {

        fun showWeatherResponse(data: List<Forecast>)

    }

    interface Presenter : WeatherContract.Presenter<View>

}
