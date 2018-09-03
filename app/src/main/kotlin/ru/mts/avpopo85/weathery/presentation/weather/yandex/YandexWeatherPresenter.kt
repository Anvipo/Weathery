package ru.mts.avpopo85.weathery.presentation.weather.yandex

import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.weather.yandex.YandexWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.weather.WeatherContract


class YandexWeatherPresenter(
        private val yandexWeatherInteractor: YandexWeatherInteractor,
        private val schedulerManagerModule: SchedulerManagerModule
) : WeatherContract.WeatherPresenter {
    private var view: WeatherContract.WeatherView? = null

    override fun onButtonClick() {
        yandexWeatherInteractor.getWeatherResponse()
                .compose(schedulerManagerModule.singleTransformer())
                .doOnSubscribe {
                    view?.showLoadingProgress()
                }
                .doAfterTerminate {
                    view?.hideLoadingProgress()
                }
                .subscribe(
                        { view?.showWeatherResponse(it) },
                        {
                            view?.showError(it)
                        }
                )
    }

    override fun onStart() {

    }

    override fun onBindView(view: WeatherContract.WeatherView) {
        this.view = view
    }

    override fun onUnbindView() {
        this.view = null
    }
}


