package ru.mts.avpopo85.weathery.presentation.base.withProgressBar

import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter

abstract class AbsProgressBarPresenter<V : BaseProgressBarContract.View> :
    AbsBasePresenter<V>() {

    override fun onUnbindView() {
        super.onUnbindView()

        view?.hideLoadingProgress()
    }

}
