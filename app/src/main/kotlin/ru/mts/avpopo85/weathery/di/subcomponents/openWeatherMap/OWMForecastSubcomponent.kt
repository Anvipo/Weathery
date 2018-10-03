package ru.mts.avpopo85.weathery.di.subcomponents.openWeatherMap

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMForecastModule
import ru.mts.avpopo85.weathery.di.scopes.openWeatherMap.OWMForecastScope
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.OWMForecastActivity

@Subcomponent(modules = [OWMForecastModule::class])
@OWMForecastScope
interface OWMForecastSubcomponent {

    fun inject(owmForecastActivity: OWMForecastActivity)

}