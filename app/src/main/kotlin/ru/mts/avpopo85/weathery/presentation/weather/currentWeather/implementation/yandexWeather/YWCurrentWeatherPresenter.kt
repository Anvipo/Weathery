package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherPresenter
@Inject constructor(
    private val interactor: ICurrentWeatherInteractor<YWCurrentWeatherType>,
    private val schedulerManagerModule: SchedulerManagerModule
) : AbsBasePresenter<CurrentWeatherContract.View<YWCurrentWeatherType>>(),
    CurrentWeatherContract.Presenter<YWCurrentWeatherType> {

    override fun loadCurrentWeather() {
        val task: Disposable = interactor.getCurrentWeather()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::onSuccess, ::onError)

        compositeDisposable.add(task)
    }

    private fun onError(error: Throwable) {
        view?.showError(error)
    }

    private fun onSuccess(weatherData: YWCurrentWeatherType) {
        view?.changeTitle(weatherData.cityName)

        if (!weatherData.isFresh) {
            view?.onNotFreshWeatherData()
        }

        view?.showWeatherResponse(weatherData)
    }

}
