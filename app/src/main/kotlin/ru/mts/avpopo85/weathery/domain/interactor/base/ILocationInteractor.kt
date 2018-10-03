package ru.mts.avpopo85.weathery.domain.interactor.base

import android.location.Address
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.Observable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.utils.UserAddressType

@Suppress("SpellCheckingInspection")
interface ILocationInteractor {

    fun requestPermissions(): Observable<Permission>

    fun getCurrentAddressOrLastKnown(): Single<UserAddressType>

}