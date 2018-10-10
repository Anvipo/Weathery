package ru.mts.avpopo85.weathery.presentation.weather.base

import ru.mts.avpopo85.weathery.presentation.base.BaseContract
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.HasProgressBar

interface WeatherContract : BaseContract {

    interface View : BaseContract.View, HasProgressBar {

        fun hideLayout()

        fun showLayout()

    }

    interface Presenter<in V : View> : BaseContract.Presenter<V>

}
