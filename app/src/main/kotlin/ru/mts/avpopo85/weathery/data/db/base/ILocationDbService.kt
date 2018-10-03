package ru.mts.avpopo85.weathery.data.db.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.common.IUserAddress

interface ILocationDbService<T : IUserAddress> {

    fun saveLocation(address: T): Single<T>

    fun getLocation(): Single<T>

}