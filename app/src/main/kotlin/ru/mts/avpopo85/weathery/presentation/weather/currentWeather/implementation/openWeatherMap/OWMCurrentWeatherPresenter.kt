package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
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
) : AbsBasePresenter<CurrentWeatherContract.View<OWMCurrentWeatherType>>(),
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
            onParameterIsNull(
                view,
                this::class.java.simpleName,
                "getCurrentWeather",
                "currentWeather"
            )
        }
    }

    private fun getCurrentWeatherOnError(error: Throwable?) {
        if (error != null) {
            view?.showError(context.parseError(error))
        } else {
            onParameterIsNull(view, this::class.java.simpleName, "getCurrentWeather", "error")
        }
    }

}
