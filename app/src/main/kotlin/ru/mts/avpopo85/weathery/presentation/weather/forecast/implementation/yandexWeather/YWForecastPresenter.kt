package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastPresenter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastType
import javax.inject.Inject

class YWForecastPresenter
@Inject constructor(
    private val interactor: IForecastInteractor<YWForecastListType>,
    private val schedulerManagerModule: SchedulerManagerModule
) : AbsForecastPresenter<YWForecastType>() {

    override fun onItemClicked(itemData: YWForecastType) {
        TODO("not implemented")
    }

    override fun loadForecast() {
        val task: Disposable = interactor.getForecast()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::onSuccess, ::onError)

        compositeDisposable.add(task)
    }

    private fun onError(error: Throwable) {
        view?.showError(error)
    }

    private fun onSuccess(weatherData: YWForecastListType) {
        view?.changeTitle(weatherData.first().cityName)

        if (!weatherData.first().isFresh) {
            view?.onNotFreshWeatherData()
        }

        view?.showWeatherResponse(weatherData)
    }

}
