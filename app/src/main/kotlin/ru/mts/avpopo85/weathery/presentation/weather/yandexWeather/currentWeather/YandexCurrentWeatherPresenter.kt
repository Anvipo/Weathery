package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.weather.WeatherContract

class YandexCurrentWeatherPresenter(
        private val yandexWeatherInteractor: YandexWeatherInteractor,
        private val schedulerManagerModule: SchedulerManagerModule
) : WeatherContract.WeatherPresenter {
    private var view: WeatherContract.WeatherView? = null

    private lateinit var disposableWork: Disposable

    override fun loadData() {
        disposableWork = yandexWeatherInteractor.getCurrentWeather()
                .compose(schedulerManagerModule.singleTransformer())
                .doOnSubscribe {
                    view?.showLoadingProgress()
                }
                .doAfterTerminate {
                    view?.hideLoadingProgress()
                }
                .subscribe(
                        {
                            view?.showWeatherResponse(it.joinToString("\n"))
                        },
                        { view?.showError(it) }
                )
    }

    override fun onStart() {
        loadData()
    }

    override fun onBindView(view: WeatherContract.WeatherView) {
        this.view = view
    }

    override fun onUnbindView() {
        this.view = null
        this.disposableWork.dispose()
    }
}


