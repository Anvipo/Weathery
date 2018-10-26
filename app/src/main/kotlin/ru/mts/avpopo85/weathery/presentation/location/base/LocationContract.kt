package ru.mts.avpopo85.weathery.presentation.location.base

import com.google.android.gms.maps.model.LatLng
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.BaseProgressBarContract
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

interface LocationContract : BaseProgressBarContract {

    interface View : BaseProgressBarContract.View {

        fun setResultAndFinish(address: UserAddressType)

        fun showGetCurrentLocationRationaleDialog()

        fun showGoSettingsForGetCurrentLocationDialog()

        fun showCityDialog(address: UserAddressType)

        fun showLocationError()

        fun showLastKnownLocationError()

        fun enableGetLastKnownLocationButton()

        fun disableGetLastKnownLocationButton()

        fun showGetAddressFromCoordinatesError(error: Throwable)

        fun startSettingsActivityForResult()

        fun getCurrentLocationByMap()

    }

    interface Presenter : BaseProgressBarContract.Presenter<View> {

        fun onGetCurrentLocationRationalePositiveClick()

        fun onGetCurrentLocationRationaleNegativeClick()

        fun onGoSettingsForGetCurrentLocationPositiveClick()

        fun onGoSettingForGetCurrentLocationNegativeClick()

        fun onGetLastKnownLocationClick()

        fun onGetCurrentGeolocationByGPSClick()

        fun onGetCurrentLocationByMapClick()

        fun saveAddress(address: UserAddressType)

        fun onApplicationSettingsRequestForGetCurrentLocationActivityResult()

        fun onLocationByMapsRequestActivityResult(coordinates: LatLng)

    }

}
