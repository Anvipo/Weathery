package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherPresenter

abstract class AbsCurrentWeatherPresenter<T : ICurrentWeather>(
    private val interactor: ICurrentWeatherInteractor<T>,
    private val schedulerManagerModule: SchedulerManagerModule
) :
    AbsWeatherPresenter<CurrentWeatherContract.View<T>>(),
    CurrentWeatherContract.Presenter<T> {

    final override fun onNewLocation() {
        val task: Disposable = interactor.onNewLocation()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doFinally { view?.hideLoadingProgress() }
            .subscribe(::onSuccessLoadingWeatherData, ::onErrorLoadingWeatherData)

        compositeDisposable.add(task)
    }

    final override fun loadWeatherData() {
        val task: Disposable = interactor.getCurrentWeather()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doFinally { view?.hideLoadingProgress() }
            .subscribe(::onSuccessLoadingWeatherData, ::onErrorLoadingWeatherData)

        compositeDisposable.add(task)
    }

    final override fun onSwipeToRefresh() {
        val task: Disposable = interactor.getCurrentWeather()
            .compose(schedulerManagerModule.singleTransformer())
            .doFinally { view?.hideRefreshingIndicator() }
            .subscribe(::onSuccessLoadingWeatherData, ::onErrorLoadingWeatherData)

        compositeDisposable.add(task)
    }

    protected open fun onSuccessLoadingWeatherData(data: T) {
        view?.changeTitle(data.cityName)

        if (!data.isFresh) {
            view?.onNotFreshWeatherData()
        }

        view?.showWeatherResponse(data)

    }

    protected open fun onErrorLoadingWeatherData(error: Throwable) {
        view?.showError(error)
    }

}