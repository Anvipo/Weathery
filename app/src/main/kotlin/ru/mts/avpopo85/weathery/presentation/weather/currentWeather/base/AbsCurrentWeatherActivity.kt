package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import kotlinx.android.synthetic.main.appbar.*
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity

abstract class AbsCurrentWeatherActivity<T : ICurrentWeather> :
    AbsWeatherActivity(),
    CurrentWeatherContract.View<T> {

    override fun changeTitle(title: String) {
        toolbar.title = title
    }

}
