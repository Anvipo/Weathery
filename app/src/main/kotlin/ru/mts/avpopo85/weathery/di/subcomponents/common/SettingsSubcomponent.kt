package ru.mts.avpopo85.weathery.di.subcomponents.common

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.modules.common.SettingsModule
import ru.mts.avpopo85.weathery.di.scopes.common.SettingsScope
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsActivity

@Subcomponent(modules = [SettingsModule::class])
@SettingsScope
interface SettingsSubcomponent {

    fun inject(locationActivity: SettingsActivity)

}
