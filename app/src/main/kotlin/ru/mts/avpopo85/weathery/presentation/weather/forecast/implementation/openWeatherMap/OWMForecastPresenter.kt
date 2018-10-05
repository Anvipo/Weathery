package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.utils.onParameterIsNull
import ru.mts.avpopo85.weathery.presentation.utils.parseError
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import javax.inject.Inject

class OWMForecastPresenter
@Inject constructor(
    private val interactor: IForecastInteractor<OWMForecastListType>,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : AbsBasePresenter<ForecastContract.View<OWMForecastListType>>(),
    ForecastContract.Presenter<OWMForecastListType> {

    override fun loadForecast() {
        val task = interactor.getForecast()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::getForecastOnSuccess, ::getForecastOnError)

        compositeDisposable.add(task)
    }

    private fun getForecastOnSuccess(forecast: OWMForecastListType?) {
        if (forecast != null) {
            view?.showWeatherResponse(forecast)
        } else {
            onParameterIsNull(
                view,
                this::class.java.simpleName,
                "getForecast",
                "forecast"
            )
        }
    }

    private fun getForecastOnError(error: Throwable?) {
        if (error != null) {
            view?.showError(context.parseError(error))
        } else {
            onParameterIsNull(view, this::class.java.simpleName, "getForecast", "error")
        }
    }

}
