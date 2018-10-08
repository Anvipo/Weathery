package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.common.IUserAddress

interface ILocationDbService<T : IUserAddress> {

    fun saveAddress(address: T): Single<T>

    fun getAddress(gpsIsEnabled: Boolean = false): Single<T>

    fun getCityName(): Single<String>

}