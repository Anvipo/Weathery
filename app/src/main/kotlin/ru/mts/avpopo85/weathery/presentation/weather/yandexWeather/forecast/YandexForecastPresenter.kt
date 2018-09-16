package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import android.content.Context
import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast.YandexForecastInteractor
import ru.mts.avpopo85.weathery.utils.makeErrorText

class YandexForecastPresenter(
    private val yandexCurrentWeatherInteractor: YandexForecastInteractor,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : ForecastContract.ForecastPresenter {
    private var view: ForecastContract.ForecastView? = null

    private lateinit var disposable: Disposable

    override fun loadData() {
        disposable = yandexCurrentWeatherInteractor.getForecast()
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

    override fun onBindView(view: ForecastContract.ForecastView) {
        this.view = view
    }

    override fun onUnbindView() {
        view = null
        if (!disposable.isDisposed)
            disposable.dispose()

    }
}


