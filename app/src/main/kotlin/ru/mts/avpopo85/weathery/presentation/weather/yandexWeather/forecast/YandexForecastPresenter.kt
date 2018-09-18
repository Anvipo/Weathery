package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast.YandexForecastInteractor
import ru.mts.avpopo85.weathery.presentation.weather.WeatherPresenter
import ru.mts.avpopo85.weathery.utils.makeErrorText
import javax.inject.Inject

class YandexForecastPresenter
@Inject constructor(
    private val yandexCurrentWeatherInteractor: YandexForecastInteractor,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : WeatherPresenter<ForecastContract.View>(), ForecastContract.Presenter {

    override fun loadData() {
        disposable = yandexCurrentWeatherInteractor.getForecast()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe {
                view?.showLoadingProgress()
            }
            .doAfterTerminate {
                view?.hideLoadingProgress()
            }
            .subscribe(
                {
                    view?.showWeatherResponse(it)
                },
                {
                    val message = context.makeErrorText(it)

                    view?.showError(message)
                }
            )
    }

}
