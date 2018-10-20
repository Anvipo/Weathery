package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherPresenter

abstract class AbsForecastPresenter<T : IForecast> :
    AbsWeatherPresenter<ForecastContract.View<T>>(),
    ForecastContract.Presenter<T>