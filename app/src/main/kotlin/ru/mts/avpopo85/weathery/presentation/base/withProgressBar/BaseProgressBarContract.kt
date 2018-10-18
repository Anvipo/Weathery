package ru.mts.avpopo85.weathery.presentation.base.withProgressBar

import ru.mts.avpopo85.weathery.presentation.base.BaseContract

interface BaseProgressBarContract : BaseContract {

    interface View : BaseContract.View {

        fun showLoadingProgress()

        fun hideLoadingProgress()

    }

    interface Presenter<in V : View> : BaseContract.Presenter<V>

}