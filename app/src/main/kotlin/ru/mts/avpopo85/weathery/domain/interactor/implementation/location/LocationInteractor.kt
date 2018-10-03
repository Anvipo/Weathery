package ru.mts.avpopo85.weathery.domain.interactor.implementation.location

import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.Observable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationInteractor
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import ru.mts.avpopo85.weathery.domain.repository.IPermissionsRepository
import javax.inject.Inject

class LocationInteractor
@Inject constructor(
    private val permissionsRepository: IPermissionsRepository,
    private val locationRepository: ILocationRepository
) : ILocationInteractor {

    override fun requestPermissions(): Observable<Permission> =
        permissionsRepository.requestLocationPermission()

    override fun getCurrentAddressOrLastKnown(): Single<UserAddressType> =
        locationRepository.getCurrentAddressOrLastKnown()

}