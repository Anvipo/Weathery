package ru.mts.avpopo85.weathery.presentation.base.common.withProgressBar.withSwipeToRefresh

import ru.mts.avpopo85.weathery.presentation.base.common.withProgressBar.BaseProgressBarContract

interface BaseSwipeToRefreshContract :
    BaseProgressBarContract {

    interface View : BaseProgressBarContract.View {

        fun hideRefreshingIndicator()

        fun hideLayout()

        fun showLayout()

    }

    interface Presenter<in V : View> : BaseProgressBarContract.Presenter<V>

}