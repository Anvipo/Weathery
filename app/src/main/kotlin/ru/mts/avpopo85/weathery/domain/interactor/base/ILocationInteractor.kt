package ru.mts.avpopo85.weathery.domain.interactor.base

import android.location.Address
import android.location.Location
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.Observable

@Suppress("SpellCheckingInspection")
interface ILocationInteractor {

    fun requestPermissions(): Observable<Permission>

    fun getLocation(): Observable<Location>

    fun kek(): Observable<Address>

}