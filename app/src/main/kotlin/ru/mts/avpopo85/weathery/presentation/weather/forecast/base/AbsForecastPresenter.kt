package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherPresenter

abstract class AbsForecastPresenter<T : IForecast>(
    private val interactor: IForecastInteractor<List<T>>,
    private val schedulerManagerModule: SchedulerManagerModule
) :
    AbsWeatherPresenter<ForecastContract.View<T>>(),
    ForecastContract.Presenter<T> {

    override fun onNewLocation() {
        val task: Disposable = interactor.onNewLocation()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doFinally { view?.hideLoadingProgress() }
            .subscribe(::onSuccessLoadingWeatherData, ::onErrorLoadingWeatherData)

        compositeDisposable.add(task)
    }

    override fun loadWeatherData() {
        val task: Disposable = interactor.getForecast()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doFinally { view?.hideLoadingProgress() }
            .subscribe(::onSuccessLoadingWeatherData, ::onErrorLoadingWeatherData)

        compositeDisposable.add(task)
    }

    override fun onSwipeToRefresh() {
        val task: Disposable = interactor.getForecast()
            .compose(schedulerManagerModule.singleTransformer())
            .doFinally { view?.hideRefreshingIndicator() }
            .subscribe(::onSuccessLoadingWeatherData, ::onErrorLoadingWeatherData)

        compositeDisposable.add(task)
    }

    override fun onItemClicked(itemData: T) {
        view?.showWeatherInfo(itemData)
    }

    protected open fun onSuccessLoadingWeatherData(data: List<T>) {
        view?.changeTitle(data.first().cityName)

        if (!data.first().isFresh) {
            view?.onNotFreshWeatherData()
        }

        view?.showWeatherResponse(data)
    }

    protected open fun onErrorLoadingWeatherData(error: Throwable) {
        view?.showError(error)
    }

}