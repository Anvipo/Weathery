package ru.mts.avpopo85.weathery.presentation.location.base

import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.presentation.base.BaseContract

interface LocationContract : BaseContract {

    interface View : BaseContract.View {

        fun showLocation(address: UserAddressType)

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