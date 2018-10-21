package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap

import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.AbsCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherPresenter
@Inject constructor(
    interactor: ICurrentWeatherInteractor<OWMCurrentWeatherType>,
    schedulerManagerModule: SchedulerManagerModule
) : AbsCurrentWeatherPresenter<OWMCurrentWeatherType>(interactor, schedulerManagerModule)