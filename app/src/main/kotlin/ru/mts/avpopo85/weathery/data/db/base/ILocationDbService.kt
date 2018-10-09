package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.common.IUserAddress

interface ILocationDbService<T : IUserAddress> {

    fun saveAddress(address: T): Single<T>

    fun getAddress(
        isGpsProviderEnabled: Boolean,
        isNetworkProviderEnabled: Boolean,
        isConnectedToInternet: Boolean
    ): Single<T>

    fun getCityName(): Single<String>

}