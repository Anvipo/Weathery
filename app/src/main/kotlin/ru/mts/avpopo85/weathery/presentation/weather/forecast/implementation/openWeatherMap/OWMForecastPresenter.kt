package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
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
            .subscribe(::onSuccess, ::onError)

        compositeDisposable.add(task)
    }

    private fun onSuccess(response: OWMForecastListType) {
        view?.changeTitle(response.first().cityName)

        view?.showWeatherResponse(response)
    }

    private fun onError(error: Throwable) {
        view?.showError(context.parseError(error))
    }

}
