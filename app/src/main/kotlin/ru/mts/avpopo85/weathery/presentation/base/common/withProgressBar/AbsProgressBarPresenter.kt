package ru.mts.avpopo85.weathery.presentation.base.common.withProgressBar

import ru.mts.avpopo85.weathery.presentation.base.common.AbsBasePresenter

abstract class AbsProgressBarPresenter<V : BaseProgressBarContract.View> :
    AbsBasePresenter<V>(),
    BaseProgressBarContract.Presenter<V> {

    override fun onUnbindView() {
        super.onUnbindView()

        view?.hideLoadingProgress()
    }

}
