package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastPresenter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType
import javax.inject.Inject

class OWMForecastPresenter
@Inject constructor(
    private val interactor: IForecastInteractor<OWMForecastListType>,
    private val schedulerManagerModule: SchedulerManagerModule
) : AbsForecastPresenter<OWMForecastType>() {

    override fun onItemClicked(itemData: OWMForecastType) {
        view?.startWeatherInfoActivity(itemData)
    }

    override fun loadForecast() {
        val task: Disposable = interactor.getForecast()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::onSuccess, ::onError)

        compositeDisposable.add(task)
    }

    private fun onSuccess(weatherData: OWMForecastListType) {
        view?.changeTitle(weatherData.first().cityName)

        if (!weatherData.first().isFresh) {
            view?.onNotFreshWeatherData()
        }

        view?.showWeatherResponse(weatherData)
    }

    private fun onError(error: Throwable) {
        view?.showError(error)
    }

}
