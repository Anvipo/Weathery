package ru.mts.avpopo85.weathery.presentation.weather.base

import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.BaseProgressBarContract

interface WeatherContract : BaseProgressBarContract {

    interface View : BaseProgressBarContract.View {

        fun hideLayout()

        fun showLayout()

        fun onNotFreshWeatherData()

    }

    interface Presenter<in V : View> : BaseProgressBarContract.Presenter<V>

}
