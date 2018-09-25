package ru.mts.avpopo85.weathery.presentation.weather.base

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter

abstract class AbsWeatherPresenter<V : WeatherContract.View> :
    AbsBasePresenter<V>(),
    WeatherContract.Presenter<V> {

    protected lateinit var disposable: Disposable

    override fun onUnbindView() {
        super.onUnbindView()

        if (this::disposable.isInitialized && !disposable.isDisposed)
            disposable.dispose()
    }

}