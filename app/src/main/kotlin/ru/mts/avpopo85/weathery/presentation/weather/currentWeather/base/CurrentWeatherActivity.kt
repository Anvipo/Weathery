package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherActivity

abstract class CurrentWeatherActivity/*<out P : CurrentWeatherContract.Presenter>*/ :
    WeatherActivity/*<WeatherContract.Presenter<WeatherContract.View>>*/() {

    //    abstract override val presenter: P

}