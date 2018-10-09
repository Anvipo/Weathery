package ru.mts.avpopo85.weathery.domain.interactor.base

import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.Observable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

interface ILocationInteractor {

    fun requestPermissions(): Observable<Permission>

    fun getCurrentAddress(): Single<UserAddressType>

    fun getLastKnownAddress(): Single<UserAddressType>

}