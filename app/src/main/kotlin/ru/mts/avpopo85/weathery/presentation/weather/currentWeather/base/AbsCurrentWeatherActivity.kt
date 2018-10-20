package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity

abstract class AbsCurrentWeatherActivity<in T : ICurrentWeather> :
    AbsWeatherActivity(),
    CurrentWeatherContract.View<T>
