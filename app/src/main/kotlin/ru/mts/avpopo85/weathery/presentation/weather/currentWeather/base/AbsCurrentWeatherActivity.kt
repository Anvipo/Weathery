package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity

abstract class AbsCurrentWeatherActivity/*<out P : CurrentWeatherContract.Presenter>*/ :
    AbsWeatherActivity/*<WeatherContract.Presenter<WeatherContract.View>>*/() {

    //    abstract override val presenter: P

}