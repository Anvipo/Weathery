package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
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
            .subscribe(
                { view?.showWeatherResponse(it) },
                { view?.showError(context.parseError(it)) }
            )
        compositeDisposable.add(task)
    }

}
