package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.AbsCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherPresenter
@Inject constructor(
    private val interactor: ICurrentWeatherInteractor<OWMCurrentWeatherType>,
    private val schedulerManagerModule: SchedulerManagerModule
) : AbsCurrentWeatherPresenter<OWMCurrentWeatherType>() {

    override fun loadCurrentWeather() {
        val task: Disposable = interactor.getCurrentWeather()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::onSuccess, ::onError)

        compositeDisposable.add(task)
    }

    private fun onSuccess(weatherData: OWMCurrentWeatherType) {
        view?.changeTitle(weatherData.cityName)

        if (!weatherData.isFresh) {
            view?.onNotFreshWeatherData()
        }

        view?.showWeatherResponse(weatherData)
    }

    private fun onError(error: Throwable) {
        view?.showError(error)
    }

}
