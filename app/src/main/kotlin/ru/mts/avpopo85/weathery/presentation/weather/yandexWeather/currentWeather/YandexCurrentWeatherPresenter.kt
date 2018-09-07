package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather.YandexCurrentWeatherInteractor

class YandexCurrentWeatherPresenter(
    private val yandexCurrentWeatherInteractor: YandexCurrentWeatherInteractor,
    private val schedulerManagerModule: SchedulerManagerModule
) : CurrentWeatherContract.CurrentWeatherPresenter {
    private var view: CurrentWeatherContract.CurrentWeatherView? = null

    private lateinit var disposableWork: Disposable

    override fun loadData() {
        disposableWork = yandexCurrentWeatherInteractor.getCurrentWeather()
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

        /*Single.zip(
            yandexCurrentWeatherInteractor.getCurrentWeather2(),
            yandexCurrentWeatherInteractor.getCurrentWeather3(),
            BiFunction(this::my)
        )
            .compose(schedulerManagerModule.singleTransformer())
            .subscribe(
                {
                    val c = 1
                }, {
                    val c = 1
                }
            )*/
    }

    override fun onStart() {
        loadData()
    }

    override fun onBindView(view: CurrentWeatherContract.CurrentWeatherView) {
        this.view = view
    }

    override fun onUnbindView() {
        this.view = null
//        this.disposableWork.dispose()
    }
}


