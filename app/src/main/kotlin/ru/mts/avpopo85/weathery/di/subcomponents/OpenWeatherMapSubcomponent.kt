package ru.mts.avpopo85.weathery.di.subcomponents

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.modules.OpenWeatherMapModule
import ru.mts.avpopo85.weathery.di.scopes.OpenWeatherMapScope
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.OWMCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.OWMForecastActivity

@Subcomponent(modules = [OpenWeatherMapModule::class])
@OpenWeatherMapScope
interface OpenWeatherMapSubcomponent {

    fun inject(owmCurrentWeatherActivity: OWMCurrentWeatherActivity)

    fun inject(owmForecastActivity: OWMForecastActivity)

}