package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.utils.parseError
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
class OWMCurrentWeatherPresenter
@Inject constructor(
    private val currentWeatherInteractor: ICurrentWeatherInteractor<OWMCurrentWeatherType>,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : AbsBasePresenter<CurrentWeatherContract.View<OWMCurrentWeatherType>>(),
    CurrentWeatherContract.Presenter<OWMCurrentWeatherType> {

    override fun loadCurrentWeather() {
        val task = currentWeatherInteractor.getCurrentWeather()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe {
                view?.showLoadingProgress()
            }
            .doAfterTerminate {
                view?.hideLoadingProgress()
            }
            .subscribe(
                { currentWeather: OWMCurrentWeatherType? ->
                    if (currentWeather != null)
                        view?.showWeatherResponse(currentWeather)
                    else {
                        view?.showError("Непридвиденная ошибка в onSuccess YWCurrentWeatherPresenter")
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

        compositeDisposable.add(task)
    }

}
