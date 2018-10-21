package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastPresenter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastType
import javax.inject.Inject

class YWForecastPresenter
@Inject constructor(
    interactor: IForecastInteractor<YWForecastListType>,
    schedulerManagerModule: SchedulerManagerModule
) : AbsForecastPresenter<YWForecastType>(interactor, schedulerManagerModule)