package ru.mts.avpopo85.weathery.domain.interactor.implementation.location

import com.tbruyelle.rxpermissions2.Permission
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

    override fun requestPermissions(): Observable<Permission> =
        permissionsRepository.requestLocationPermission()

    override fun getCurrentAddress(): Single<UserAddressType> =
        locationRepository.getCurrentAddress()

    override fun getLastKnownAddress(): Single<UserAddressType> =
        locationRepository.getLastKnownAddress()

}