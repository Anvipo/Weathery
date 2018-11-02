package ru.mts.avpopo85.weathery.domain.repository

import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

interface ILocationRepository {

    fun getCurrentAddressByGPS(): Single<UserAddressType>

    fun getLastKnownAddress(): Single<UserAddressType>

    fun getAddressFromCoordinates(coordinates: LatLng?): Single<UserAddressType>

    fun saveAddressToDB(address: UserAddressType): Single<UserAddressType>

    fun saveAddressToSharedPreferences(address: UserAddressType): Single<UserAddressType>

    fun checkInternetConnection(): Completable

}
