package ru.mts.avpopo85.weathery.domain.repository

import io.reactivex.Single
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

interface ILocationRepository {

    fun getCurrentAddress(): Single<UserAddressType>

    fun getLastKnownAddress(): Single<UserAddressType>

}