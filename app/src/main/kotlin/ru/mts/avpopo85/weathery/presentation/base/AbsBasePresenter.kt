package ru.mts.avpopo85.weathery.presentation.base

import io.reactivex.disposables.CompositeDisposable


abstract class AbsBasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    protected var view: V? = null

    protected val compositeDisposable = CompositeDisposable()

    override fun onBindView(view: V) {
        this.view = view
    }

    override fun onUnbindView() {
        compositeDisposable.clear()

        this.view = null
    }

}
