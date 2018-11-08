package ru.mts.avpopo85.weathery.domain.interactor.implementation.settings

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.ISettingsInteractor
import ru.mts.avpopo85.weathery.domain.model.implementation.settings.SettingsInfo
import ru.mts.avpopo85.weathery.domain.repository.ISettingsRepository
import javax.inject.Inject

class SettingsInteractor
@Inject constructor(
    private val repository: ISettingsRepository
) : ISettingsInteractor {

    override fun checkPreferences(): Single<SettingsInfo> = repository.checkPreferences()

}