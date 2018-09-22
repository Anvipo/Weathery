package ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.base

import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherActivity

abstract class ForecastActivity/*<out P : ForecastContract.Presenter>*/ :
    WeatherActivity/*<WeatherContract.Presenter<WeatherContract.View>>*/() {

    //    abstract override val presenter: P

}