package ru.mts.avpopo85.weathery.presentation.weather.base

import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.withSwipeToRefresh.BaseSwipeToRefreshContract

interface WeatherContract : BaseSwipeToRefreshContract {

    interface View : BaseSwipeToRefreshContract.View {

        fun onNotFreshWeatherData()

    }

    interface Presenter<in V : View> : BaseSwipeToRefreshContract.Presenter<V> {

        fun onSwipeToRefresh()

        fun loadWeatherData()

    }

}
