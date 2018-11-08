package ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.locationPreference

import ru.mts.avpopo85.weathery.domain.model.implementation.settings.LocationInfo
import ru.mts.avpopo85.weathery.presentation.base.common.BaseContract

interface LocationPreferenceContract : BaseContract {

    interface View : BaseContract.View {

        fun onSuccessCheckLocationInfo(locationInfo: LocationInfo)

        fun onErrorCheckLocationInfo(error: Throwable)

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun checkLocationInfo()

    }

}
