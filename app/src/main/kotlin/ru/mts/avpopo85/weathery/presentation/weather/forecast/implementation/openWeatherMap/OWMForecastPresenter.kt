package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastPresenter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType
import javax.inject.Inject

class OWMForecastPresenter
@Inject constructor(
    interactor: IForecastInteractor<OWMForecastListType>,
    schedulerManagerModule: SchedulerManagerModule
) : AbsForecastPresenter<OWMForecastType>(interactor, schedulerManagerModule)