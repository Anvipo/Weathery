package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.AbsProgressBarPresenter

abstract class AbsCurrentWeatherPresenter<T : ICurrentWeather> :
    AbsProgressBarPresenter<CurrentWeatherContract.View<T>>(),
    CurrentWeatherContract.Presenter<T>