package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import android.view.View
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity

abstract class AbsCurrentWeatherActivity<V : View>/*<out P : CurrentWeatherContract.Presenter>*/ :
    AbsWeatherActivity<V>/*<WeatherContract.Presenter<WeatherContract.View>>*/() {

    //    abstract override val presenter: P

}
