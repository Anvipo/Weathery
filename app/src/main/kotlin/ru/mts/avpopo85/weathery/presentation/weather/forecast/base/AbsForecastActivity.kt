package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import android.view.View
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity

abstract class AbsForecastActivity<V : View, T : Collection<IForecast>>/*<out P : ForecastContract.Presenter>*/ :
    AbsWeatherActivity<V>/*<WeatherContract.Presenter<WeatherContract.View>>*/() {

    //    abstract override val presenter: P

    protected abstract fun putForecastDataInPager(data: T)

}