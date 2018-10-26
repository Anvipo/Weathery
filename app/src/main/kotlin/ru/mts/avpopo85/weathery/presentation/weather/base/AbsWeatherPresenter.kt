package ru.mts.avpopo85.weathery.presentation.weather.base

import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.withSwipeToRefresh.AbsSwipeToRefreshPresenter

abstract class AbsWeatherPresenter<V : WeatherContract.View> :
    AbsSwipeToRefreshPresenter<V>(),
    WeatherContract.Presenter<V>