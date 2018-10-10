package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.AbsProgressBarPresenter
import ru.mts.avpopo85.weathery.presentation.utils.onParameterIsNull
import ru.mts.avpopo85.weathery.presentation.utils.parseError
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherPresenter
@Inject constructor(
    private val interactor: ICurrentWeatherInteractor<OWMCurrentWeatherType>,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : AbsProgressBarPresenter<CurrentWeatherContract.View<OWMCurrentWeatherType>>(),
    CurrentWeatherContract.Presenter<OWMCurrentWeatherType> {

    override fun loadCurrentWeather() {
        val task = interactor.getCurrentWeather()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::getCurrentWeatherOnSuccess, ::getCurrentWeatherOnError)

        compositeDisposable.add(task)
    }

    private fun getCurrentWeatherOnSuccess(currentWeather: OWMCurrentWeatherType?) {
        if (currentWeather != null) {
            view?.showWeatherResponse(currentWeather)
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "getCurrentWeatherOnSuccess"

                onParameterIsNull(view, this::class.java.simpleName, methodName, "currentWeather")
            }
        }
    }

    private fun getCurrentWeatherOnError(error: Throwable?) {
        if (error != null) {
            view?.showError(context.parseError(error))
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name ?: "getCurrentWeatherOnError"

                onParameterIsNull(view, this::class.java.simpleName, methodName, "error")
            }
        }
    }

}
