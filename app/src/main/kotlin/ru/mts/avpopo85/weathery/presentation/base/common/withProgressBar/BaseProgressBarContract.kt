package ru.mts.avpopo85.weathery.presentation.base.common.withProgressBar

import ru.mts.avpopo85.weathery.presentation.base.common.BaseContract

interface BaseProgressBarContract :
    BaseContract {

    interface View : BaseContract.View {

        fun showLoadingProgress()

        fun hideLoadingProgress()

    }

    interface Presenter<in V : View> : BaseContract.Presenter<V>

}