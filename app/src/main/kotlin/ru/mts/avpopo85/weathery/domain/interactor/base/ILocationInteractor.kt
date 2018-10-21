package ru.mts.avpopo85.weathery.domain.interactor.base

import com.google.android.gms.maps.model.LatLng
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.Observable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

interface ILocationInteractor {

    fun requestLocationPermissions(): Observable<Permission>

    fun getCurrentAddressByGPS(): Single<UserAddressType>

    fun getLastKnownAddress(): Single<UserAddressType>

    fun getAddressFromCoordinates(coordinates: LatLng): Single<UserAddressType>

    fun saveAddress(address: UserAddressType): Single<UserAddressType>

}
