package ru.mts.avpopo85.weathery.data.repository.common

import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import ru.mts.avpopo85.weathery.domain.repository.IPermissionsRepository
import javax.inject.Inject

class PermissionsRepository
@Inject constructor(private val rxPermissions: RxPermissions) : IPermissionsRepository {

    override fun requestLocationPermissions(): Observable<Permission> =
        rxPermissions.requestEachCombined(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

}
