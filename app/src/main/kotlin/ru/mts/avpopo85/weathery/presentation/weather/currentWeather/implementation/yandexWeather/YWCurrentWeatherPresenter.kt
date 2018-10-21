package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather

import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.AbsCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherPresenter
@Inject constructor(
    interactor: ICurrentWeatherInteractor<YWCurrentWeatherType>,
    schedulerManagerModule: SchedulerManagerModule
) : AbsCurrentWeatherPresenter<YWCurrentWeatherType>(interactor, schedulerManagerModule)
