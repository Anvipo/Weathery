package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.utils.onParameterIsNull
import ru.mts.avpopo85.weathery.presentation.utils.parseError
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherPresenter
@Inject constructor(
    private val interactor: ICurrentWeatherInteractor<YWCurrentWeatherType>,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : AbsBasePresenter<CurrentWeatherContract.View<YWCurrentWeatherType>>(),
    CurrentWeatherContract.Presenter<YWCurrentWeatherType> {

    override fun loadCurrentWeather() {
        val task = interactor.getCurrentWeather()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::getCurrentWeatherOnSuccess, ::getCurrentWeatherOnError)

        compositeDisposable.add(task)
    }

    private fun getCurrentWeatherOnSuccess(currentWeather: YWCurrentWeatherType?) {
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
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "getCurrentWeatherOnError"

                onParameterIsNull(view, this::class.java.simpleName, methodName, "error")
            }
        }
    }

}
