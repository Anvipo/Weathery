package ru.mts.avpopo85.weathery.presentation.location.base

import com.google.android.gms.maps.model.LatLng
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.BaseProgressBarContract
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

interface LocationContract : BaseProgressBarContract {

    interface View : BaseProgressBarContract.View {

        fun showRationaleDialog()

        fun showGoSettingsDialog()

        fun showCityDialog(address: UserAddressType)

        fun startMainActivityAndFinish()

        fun showLocationError()

        fun showLastKnownLocationError()

        fun enableGetLastKnownLocationButton()

        fun disableGetLastKnownLocationButton()

        fun showGetAddressFromCoordinatesError(error: Throwable)

        fun startSettingsActivityForResult()

    }

    interface Presenter : BaseProgressBarContract.Presenter<View> {

        fun getCurrentGeolocation()

        fun getLastKnownGeolocation()

        fun onRationalePositiveClick()

        fun onRationaleNegativeClick()

        fun onGoSettingsPositiveClick()

        fun onGoSettingNegativeClick()

        fun onActivityResult()

        fun getAddressFromCoordinates(coordinates: LatLng)

        fun saveAddress(address: UserAddressType)

    }

}
