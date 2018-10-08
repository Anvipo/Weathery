package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.utils.onParameterIsNull
import ru.mts.avpopo85.weathery.presentation.utils.parseError
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType
import javax.inject.Inject

class YWForecastPresenter
@Inject constructor(
    private val interactor: IForecastInteractor<YWForecastListType>,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : AbsBasePresenter<ForecastContract.View<YWForecastListType>>(),
    ForecastContract.Presenter<YWForecastListType> {

    override fun loadForecast() {
        val task = interactor.getForecast()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::getForecastOnSuccess, ::getForecastOnError)
        compositeDisposable.add(task)
    }

    private fun getForecastOnSuccess(forecast: YWForecastListType?) {
        if (forecast != null) {
            view?.showWeatherResponse(forecast)
        } else {
            val methodName =
                object : Any() {}.javaClass.enclosingMethod?.name ?: "getForecastOnSuccess"

            onParameterIsNull(
                view,
                this::class.java.simpleName,
                methodName,
                "forecast"
            )
        }
    }

    private fun getForecastOnError(error: Throwable?) {
        if (error != null) {
            view?.showError(context.parseError(error))
        } else {
            val methodName =
                object : Any() {}.javaClass.enclosingMethod?.name ?: "getForecastOnError"

            onParameterIsNull(view, this::class.java.simpleName, methodName, "error")
        }
    }

}
