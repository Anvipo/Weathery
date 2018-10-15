package ru.mts.avpopo85.weathery.presentation.base.withProgressBar

import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.base.BaseContract

abstract class AbsProgressBarPresenter<V> :
    AbsBasePresenter<V>() where V : BaseContract.View, V : HasProgressBar {

    override fun onUnbindView() {
        super.onUnbindView()

        view?.hideLoadingProgress()
    }

}
