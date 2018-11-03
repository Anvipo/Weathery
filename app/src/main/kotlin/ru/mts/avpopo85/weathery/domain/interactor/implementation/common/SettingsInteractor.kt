package ru.mts.avpopo85.weathery.domain.interactor.implementation.common

import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.ISettingsInteractor
import ru.mts.avpopo85.weathery.domain.repository.ISettingsRepository
import ru.mts.avpopo85.weathery.utils.common.SettingsInfo
import javax.inject.Inject

class SettingsInteractor
@Inject constructor(
    private val settingsRepository: ISettingsRepository
) : ISettingsInteractor {

    override fun checkPreferences(): Single<SettingsInfo> = settingsRepository.checkPreferences()

}