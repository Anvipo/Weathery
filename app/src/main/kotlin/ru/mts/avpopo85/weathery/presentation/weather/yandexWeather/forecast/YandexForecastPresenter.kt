package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast.YandexForecastInteractor

class YandexForecastPresenter(
    private val yandexCurrentWeatherInteractor: YandexForecastInteractor,
    private val schedulerManagerModule: SchedulerManagerModule
) : ForecastContract.ForecastPresenter {
    private var view: ForecastContract.ForecastView? = null

    private lateinit var disposableWork: Disposable

    override fun loadData() {
        disposableWork = yandexCurrentWeatherInteractor.getForecast()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe {
                view?.showLoadingProgress()
            }
            .doAfterTerminate {
                view?.hideLoadingProgress()
            }
            .subscribe(
                {
                    view?.showWeatherResponse(it)
                },
                {
                    view?.showError(it)
                }
            )
    }

    override fun onStart() {
        loadData()
    }

    override fun onBindView(view: ForecastContract.ForecastView) {
        this.view = view
    }

    override fun onUnbindView() {
        this.view = null
        this.disposableWork.dispose()
    }
}


