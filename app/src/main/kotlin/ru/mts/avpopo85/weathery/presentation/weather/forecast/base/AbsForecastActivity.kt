package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity

abstract class AbsForecastActivity<T : Collection<IForecast>> : AbsWeatherActivity() {

    protected abstract fun putForecastDataInPager(data: T)

}
