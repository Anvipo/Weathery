package ru.mts.avpopo85.weathery.presentation.location.base

import ru.mts.avpopo85.weathery.presentation.base.BaseContract

interface LocationContract : BaseContract {

    interface View : BaseContract.View {

        fun showRationaleDialog()

        fun showGoSettingsDialog()

        fun showLocationDialog(city: String?)

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun getCurrentGeolocation()

        fun getLastKnownGeolocation()

        fun onRationalePositiveClick()

        fun onRationaleNegativeClick()

        fun onGoSettingsPositiveClick()

        fun onGoSettingNegativeClick()

        fun onActivityResult()

    }

}