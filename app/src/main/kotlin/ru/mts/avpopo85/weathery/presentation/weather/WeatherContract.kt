package ru.mts.avpopo85.weathery.presentation.weather

import ru.mts.avpopo85.weathery.presentation.base.BaseContract

interface WeatherContract : BaseContract {

    interface WeatherView : BaseContract.View {
        fun showLoadingProgress()
        fun hideLoadingProgress()
        fun showError(throwable: Throwable)
        fun showError(message: String?)
    }

//    interface WeatherPresenter : BaseContract.Presenter<WeatherView>
}