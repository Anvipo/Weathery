package ru.mts.avpopo85.weathery.di.modules.common

import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.common.SettingsScope
import ru.mts.avpopo85.weathery.domain.interactor.base.ISettingsInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.common.SettingsInteractor
import ru.mts.avpopo85.weathery.domain.repository.ISettingsRepository
import ru.mts.avpopo85.weathery.presentation.settings.base.SettingsContract
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsPresenter

@Module
class SettingsModule {

    @Provides
    @SettingsScope
    fun provideOWMCurrentWeatherPresenter(
        interactor: ISettingsInteractor,
        schedulerManagerModule: SchedulerManagerModule
    ): SettingsContract.Presenter =
        SettingsPresenter(interactor, schedulerManagerModule)

    @Provides
    @SettingsScope
    fun provideOWMCurrentWeatherInteractor(
        repository: ISettingsRepository
    ): ISettingsInteractor = SettingsInteractor(repository)

}
