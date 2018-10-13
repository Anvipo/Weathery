package ru.mts.avpopo85.weathery.data.network.utils

import android.location.Location
import io.reactivex.Single
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

interface IGeocoder {

    fun geocodeLocation(location: Location): Single<UserAddressType>

}
