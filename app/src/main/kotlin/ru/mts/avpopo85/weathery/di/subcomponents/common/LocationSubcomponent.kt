package ru.mts.avpopo85.weathery.di.subcomponents.common

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.modules.common.LocationModule
import ru.mts.avpopo85.weathery.di.scopes.common.LocationScope
import ru.mts.avpopo85.weathery.di.scopes.openWeatherMap.OWMCurrentWeatherScope
import ru.mts.avpopo85.weathery.presentation.location.LocationActivity

@Suppress("SpellCheckingInspection")
@Subcomponent(modules = [LocationModule::class])
@LocationScope
interface LocationSubcomponent {

    fun inject(locationActivity: LocationActivity)

}