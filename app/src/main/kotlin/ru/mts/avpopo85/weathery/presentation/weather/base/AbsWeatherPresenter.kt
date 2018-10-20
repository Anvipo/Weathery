package ru.mts.avpopo85.weathery.presentation.weather.base

import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.AbsProgressBarPresenter

abstract class AbsWeatherPresenter<V : WeatherContract.View> :
    AbsProgressBarPresenter<V>(),
    WeatherContract.Presenter<V>