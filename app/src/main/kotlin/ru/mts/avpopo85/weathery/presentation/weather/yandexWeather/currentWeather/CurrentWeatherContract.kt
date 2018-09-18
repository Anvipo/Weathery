package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import ru.mts.avpopo85.weathery.domain.models.CurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.WeatherContract

interface CurrentWeatherContract : WeatherContract {

    interface View : WeatherContract.View {

        fun showWeatherResponse(data: CurrentWeather)

    }

    interface Presenter : WeatherContract.Presenter<View>

}
