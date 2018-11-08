package ru.mts.avpopo85.weathery.presentation.settings.base

import ru.mts.avpopo85.weathery.domain.model.implementation.settings.SettingsInfo
import ru.mts.avpopo85.weathery.presentation.base.common.BaseContract

interface SettingsContract : BaseContract {

    interface View : BaseContract.View {

        fun onSuccessCheckPreferences(settingsInfo: SettingsInfo)

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun checkPreferences()

    }

}