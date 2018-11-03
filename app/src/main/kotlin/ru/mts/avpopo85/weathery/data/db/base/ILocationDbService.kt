package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserAddress

interface ILocationDbService {

    fun saveCurrentAddress(address: UserAddress): Single<UserAddress>

    fun getLastKnownAddress(
        isGpsProviderEnabled: Boolean,
        isNetworkProviderEnabled: Boolean,
        isConnectedToInternet: Boolean
    ): Single<UserAddress>

    fun getLastKnownCityName(): Single<String>

}
