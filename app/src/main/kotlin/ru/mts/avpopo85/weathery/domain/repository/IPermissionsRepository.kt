package ru.mts.avpopo85.weathery.domain.repository

import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.Observable

interface IPermissionsRepository {

    fun requestLocationPermissions(): Observable<Permission>

}