package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import android.content.Context
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.models.CurrentWeather
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather.YandexCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.weather.WeatherPresenter
import ru.mts.avpopo85.weathery.utils.makeErrorText
import javax.inject.Inject

class YandexCurrentWeatherPresenter
@Inject constructor(
    private val yandexCurrentWeatherInteractor: YandexCurrentWeatherInteractor,
    private val schedulerManagerModule: SchedulerManagerModule,
    private val context: Context
) : WeatherPresenter<CurrentWeatherContract.View>(), CurrentWeatherContract.Presenter {

    override fun loadData() {
        disposable = yandexCurrentWeatherInteractor.getCurrentWeather()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe {
                view?.showLoadingProgress()
            }
            .doAfterTerminate {
                view?.hideLoadingProgress()
            }.subscribe({ currentWeather: CurrentWeather? ->
                if (currentWeather != null) view?.showWeatherResponse(currentWeather)
            }, { throwable: Throwable? ->
                if (throwable != null) {
                    val message = context.makeErrorText(throwable)

                    view?.showError(message)
                }
            })
    }

}
