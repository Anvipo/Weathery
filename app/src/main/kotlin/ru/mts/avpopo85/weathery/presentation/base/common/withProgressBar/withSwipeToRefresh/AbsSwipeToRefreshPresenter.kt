package ru.mts.avpopo85.weathery.presentation.base.common.withProgressBar.withSwipeToRefresh

import ru.mts.avpopo85.weathery.presentation.base.common.withProgressBar.AbsProgressBarPresenter

abstract class AbsSwipeToRefreshPresenter<V : BaseSwipeToRefreshContract.View> :
    AbsProgressBarPresenter<V>(),
    BaseSwipeToRefreshContract.Presenter<V>