package ru.mts.avpopo85.weathery.domain.interactor.implementation.location

import com.google.android.gms.maps.model.LatLng
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationInteractor
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import ru.mts.avpopo85.weathery.domain.repository.IPermissionsRepository
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import javax.inject.Inject

class LocationInteractor
@Inject constructor(
    private val permissionsRepository: IPermissionsRepository,
    private val locationRepository: ILocationRepository
) : ILocationInteractor {

    override fun checkInternetConnection(): Completable =
        locationRepository.checkInternetConnection()

    override fun requestLocationPermissions(): Observable<Permission> =
        permissionsRepository.requestLocationPermissions()

    override fun getCurrentAddressByGPS(): Single<UserAddressType> =
        locationRepository.getCurrentAddressByGPS()

    override fun getLastKnownAddress(): Single<UserAddressType> =
        locationRepository.getLastKnownAddress()

    override fun getAddressFromCoordinates(coordinates: LatLng): Single<UserAddressType> =
        locationRepository.getAddressFromCoordinates(coordinates)

    override fun saveAddress(address: UserAddressType): Single<UserAddressType> =
        locationRepository.saveAddress(address)

}
