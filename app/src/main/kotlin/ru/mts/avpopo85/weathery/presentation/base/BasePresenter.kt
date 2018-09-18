package ru.mts.avpopo85.weathery.presentation.base


abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    protected var view: V? = null

    override fun onBindView(view: V) {
        this.view = view
    }

    override fun onUnbindView() {
        this.view = null
    }

}
