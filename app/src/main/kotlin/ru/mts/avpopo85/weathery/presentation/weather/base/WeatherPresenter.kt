package ru.mts.avpopo85.weathery.presentation.weather.base

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.presentation.base.BasePresenter

abstract class WeatherPresenter<V : WeatherContract.View> :
    BasePresenter<V>(),
    WeatherContract.Presenter<V> {

    protected lateinit var disposable: Disposable

    override fun onUnbindView() {
        super.onUnbindView()

        if (!disposable.isDisposed) disposable.dispose()
    }

}