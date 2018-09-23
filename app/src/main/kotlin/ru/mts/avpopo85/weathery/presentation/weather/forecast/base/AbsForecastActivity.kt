package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity

abstract class AbsForecastActivity/*<out P : ForecastContract.Presenter>*/ :
    AbsWeatherActivity/*<WeatherContract.Presenter<WeatherContract.View>>*/() {

    //    abstract override val presenter: P

}