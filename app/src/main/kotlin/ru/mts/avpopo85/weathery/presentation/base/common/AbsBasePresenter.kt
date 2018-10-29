package ru.mts.avpopo85.weathery.presentation.base.common

import io.reactivex.disposables.CompositeDisposable


abstract class AbsBasePresenter<V : BaseContract.View> :
    BaseContract.Presenter<V> {

    final override fun onBindView(view: V) {
        this.view = view
    }

    override fun onUnbindView() {
        clearCompositeDisposable()

        this.view = null
    }

    final override fun clearCompositeDisposable() {
        compositeDisposable.clear()
    }

    protected var view: V? = null

    protected val compositeDisposable = CompositeDisposable()

}
