package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import android.content.Context
import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather.YandexCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.utils.makeErrorText

class YandexCurrentWeatherPresenter(
    private val yandexCurrentWeatherInteractor: YandexCurrentWeatherInteractor,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
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
                    val message = context.makeErrorText(it)

                    view?.showError(message)
                }
            )
    }

    override fun onStart() {
        loadData()
    }

    override fun onBindView(view: CurrentWeatherContract.CurrentWeatherView) {
        this.view = view
    }

    override fun onUnbindView() {
        this.view = null
        this.disposableWork.dispose()
    }
}


