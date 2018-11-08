package ru.mts.avpopo85.weathery.di.subcomponents.common

import dagger.Subcomponent
import ru.mts.avpopo85.weathery.di.modules.common.SettingsModule
import ru.mts.avpopo85.weathery.di.scopes.common.SettingsScope
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsActivity
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.locationPreference.LocationPreferenceFragment

@Subcomponent(modules = [SettingsModule::class])
@SettingsScope
interface SettingsSubcomponent {

    fun inject(locationActivity: SettingsActivity)

    fun inject(locationPreferenceFragment: LocationPreferenceFragment)

}
