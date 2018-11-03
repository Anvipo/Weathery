package ru.mts.avpopo85.weathery.domain.interactor.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.utils.common.SettingsInfo

interface ISettingsInteractor {

    fun checkPreferences(): Single<SettingsInfo>

}