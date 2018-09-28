package ru.mts.avpopo85.weathery.domain.interactor.implementation.location

import android.location.Address
import android.location.Location
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.Observable
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationInteractor
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import ru.mts.avpopo85.weathery.domain.repository.IPermissionsRepository
import javax.inject.Inject

@Suppress("SpellCheckingInspection")
class LocationInteractor
@Inject constructor(
    private val permissionsRepository: IPermissionsRepository,
    private val locationRepository: ILocationRepository
) : ILocationInteractor {

    override fun requestPermissions(): Observable<Permission> =
        permissionsRepository.requestLocationPermission()

    override fun getLocation(): Observable<Location> = locationRepository.getLocation()

    override fun kek(): Observable<Address> = locationRepository.kek()
}