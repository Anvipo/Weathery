package ru.mts.avpopo85.weathery.di.subcomponents.openWeatherMap

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMCurrentWeatherModule
import ru.mts.avpopo85.weathery.di.scopes.openWeatherMap.OWMCurrentWeatherScope
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.OWMCurrentWeatherFragment

@Subcomponent(modules = [OWMCurrentWeatherModule::class])
@OWMCurrentWeatherScope
interface OWMCurrentWeatherSubcomponent {

    fun inject(owmCurrentWeatherFragment: OWMCurrentWeatherFragment)

}
