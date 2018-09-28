package ru.mts.avpopo85.weathery.presentation.location.base

import android.location.Address
import ru.mts.avpopo85.weathery.presentation.base.BaseContract

interface LocationContract : BaseContract {

    interface View : BaseContract.View {

        fun showLocation(address: Address)

        fun showRationaleDialog()

        fun showGoSettingsDialog()

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun getLocation()

        fun onRationalePositiveClick()

        fun onRationaleNegativeClick()

        fun onGoSettingsPositiveClick()

        fun onGoSettingNegativeClick()

        fun onActivityResult()

    }

}