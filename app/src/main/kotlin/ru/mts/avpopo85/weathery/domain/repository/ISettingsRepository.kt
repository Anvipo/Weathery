package ru.mts.avpopo85.weathery.domain.repository

import io.reactivex.Single
import ru.mts.avpopo85.weathery.utils.common.SettingsInfo

interface ISettingsRepository {

    fun checkPreferences(): Single<SettingsInfo>

}
