package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.CurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.utils.makeErrorText
import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherPresenter
@Inject constructor(
    private val currentWeatherInteractor: CurrentWeatherInteractor<YWCurrentWeatherType>,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : WeatherPresenter<CurrentWeatherContract.View>(),
    CurrentWeatherContract.Presenter {

    override fun loadCurrentWeather() {
        disposable = currentWeatherInteractor.getCurrentWeather()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe {
                view?.showLoadingProgress()
            }
            .doAfterTerminate {
                view?.hideLoadingProgress()
            }
            .subscribe(
                { ywCurrentWeather: YWCurrentWeatherType? ->
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
