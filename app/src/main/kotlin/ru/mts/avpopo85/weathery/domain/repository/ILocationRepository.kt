package ru.mts.avpopo85.weathery.domain.repository

import android.location.Address
import android.location.Location
import io.reactivex.Observable
import io.reactivex.Single

interface ILocationRepository {

    fun getLocation(): Observable<Location>

    fun getNewLocation(): Single<Location>

    fun kek(): Observable<Address>

}