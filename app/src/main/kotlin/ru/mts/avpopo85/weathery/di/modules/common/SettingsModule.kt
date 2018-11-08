package ru.mts.avpopo85.weathery.di.modules.common

import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.common.SettingsScope
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationPreferenceInteractor
import ru.mts.avpopo85.weathery.domain.interactor.base.ISettingsInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.settings.LocationPreferenceInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.settings.SettingsInteractor
import ru.mts.avpopo85.weathery.domain.repository.ILocationPreferenceRepository
import ru.mts.avpopo85.weathery.domain.repository.ISettingsRepository
import ru.mts.avpopo85.weathery.presentation.settings.base.SettingsContract
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsPresenter
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.locationPreference.LocationPreferenceContract
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.locationPreference.LocationPreferencePresenter

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun provideSettingsPresenter(
        interactor: ISettingsInteractor,
        schedulerManagerModule: SchedulerManagerModule
    ): SettingsContract.Presenter = SettingsPresenter(interactor, schedulerManagerModule)

    @Provides
    @SettingsScope
    fun provideSettingsInteractor(
        repository: ISettingsRepository
    ): ISettingsInteractor = SettingsInteractor(repository)

    @Provides
    @SettingsScope
    fun provideLocationPreferencePresenter(
        interactor: ILocationPreferenceInteractor,
        schedulerManagerModule: SchedulerManagerModule
    ): LocationPreferenceContract.Presenter =
        LocationPreferencePresenter(interactor, schedulerManagerModule)

    @Provides
    @SettingsScope
    fun provideLocationPreferenceInteractor(
        repository: ILocationPreferenceRepository
    ): ILocationPreferenceInteractor = LocationPreferenceInteractor(repository)

}
