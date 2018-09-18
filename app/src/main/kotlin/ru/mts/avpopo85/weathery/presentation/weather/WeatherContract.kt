package ru.mts.avpopo85.weathery.presentation.weather

import ru.mts.avpopo85.weathery.presentation.base.BaseContract

interface WeatherContract : BaseContract {

    interface View : BaseContract.View {

        fun showLoadingProgress()

        fun hideLoadingProgress()

    }

    interface Presenter<T> : BaseContract.Presenter<T> {

        fun onStart()

        fun loadData()

    }

}
