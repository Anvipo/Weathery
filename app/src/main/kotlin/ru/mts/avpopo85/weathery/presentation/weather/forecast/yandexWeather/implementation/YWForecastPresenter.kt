package ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.implementation

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ForecastInteractor
import ru.mts.avpopo85.weathery.presentation.utils.makeErrorText
import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.base.ForecastContract
import ru.mts.avpopo85.weathery.utils.YWForecastType
import javax.inject.Inject

class YWForecastPresenter
@Inject constructor(
    private val currentWeatherInteractor: ForecastInteractor<YWForecastType>,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : WeatherPresenter<ForecastContract.View>(),
    ForecastContract.Presenter {

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
                { ywCurrentWeather: YWForecastType? ->
                    if (ywCurrentWeather != null)
                        view?.showWeatherResponse(ywCurrentWeather)
                },
                { throwable: Throwable? ->
                    if (throwable != null) {
                        val message = context.makeErrorText(throwable)

                        view?.showError(message)
                    }
                }
            )
    }

}
