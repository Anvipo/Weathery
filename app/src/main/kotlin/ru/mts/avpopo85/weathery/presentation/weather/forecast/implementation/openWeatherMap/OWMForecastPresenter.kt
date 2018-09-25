package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.presentation.utils.parseError
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import javax.inject.Inject

class OWMForecastPresenter
@Inject constructor(
    private val currentWeatherInteractor: IForecastInteractor<OWMForecastListType>,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : AbsWeatherPresenter<ForecastContract.View<OWMForecastListType>>(),
    ForecastContract.Presenter<OWMForecastListType> {

    override fun loadForecast() {
        disposable = currentWeatherInteractor.getForecast()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe {
                view?.showLoadingProgress()
            }
            .doAfterTerminate {
                view?.hideLoadingProgress()
            }
            .subscribe(
                { currentWeather: OWMForecastListType? ->
                    if (currentWeather != null)
                        view?.showWeatherResponse(currentWeather)
                    else {
                        view?.showError("Непредвиденная ошибка в ${this::class.java.simpleName}.onSuccess")
                    }
                },
                { throwable: Throwable? ->
                    if (throwable != null) {
                        val message = context.parseError(throwable)

                        view?.showError(message)
                    } else {
                        view?.showError("Непредвиденная ошибка в ${this::class.java.simpleName}.onError")
                    }
                }
            )
    }

}
