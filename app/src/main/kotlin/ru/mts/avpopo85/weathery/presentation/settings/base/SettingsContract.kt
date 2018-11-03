package ru.mts.avpopo85.weathery.presentation.settings.base

import ru.mts.avpopo85.weathery.presentation.base.common.BaseContract
import ru.mts.avpopo85.weathery.utils.common.SettingsInfo

interface SettingsContract : BaseContract {

    interface View : BaseContract.View {

        fun onSuccessCheckPreferences(settingsInfo: SettingsInfo)

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun checkPreferences()

    }

}