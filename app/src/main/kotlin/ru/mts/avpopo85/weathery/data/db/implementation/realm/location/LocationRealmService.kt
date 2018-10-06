package ru.mts.avpopo85.weathery.data.db.implementation.realm.location

import android.content.Context
import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.data.utils.UserAddressType

class LocationRealmService(private val context: Context) : ILocationDbService<UserAddressType> {

    override fun saveLocation(address: UserAddressType):
            Single<UserAddressType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: UserAddressType? = null

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(address)
                }

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: UserAddressType? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null) {
                        emitter.onSuccess(data)
                    } else {
                        onDataIsNull(emitter, "saveLocation", this::class.java.simpleName)
                    }
                } else {
                    onProxyDataIsNull(emitter, "saveLocation", this::class.java.simpleName)
                }
            }
        }

    override fun getLocation(gpsIsEnabled: Boolean): Single<UserAddressType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData =
                    realmInstance
                        .where<UserAddressType>()
                        .findFirst()

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: UserAddressType? = realmInstance.copyFromRealm(proxyData!!)

                    if (data != null) {
                        //TODO наверно нужно проверять данные на устаревание
                        emitter.onSuccess(data)
                    } else {
                        onDataIsNull(
                            emitter,
                            "getCurrentGeolocation",
                            this::class.java.simpleName
                        )
                    }
                } else if (!gpsIsEnabled) {
                    val part1 = context.getString(R.string.db_has_nothing)
                    val part2 = context.getString(R.string.you_dont_turn_on_gps)

                    emitter.onError(Throwable("$part1, $part2"))
                } else if (gpsIsEnabled) {
                    val part1 = context.getString(R.string.db_has_nothing)
                    val part2 = context.getString(R.string.get_geolocation_by_gps)

                    emitter.onError(Throwable("$part1. $part2"))
                } else if (!dataExistsInDB) {
                    onProxyDataIsNull(
                        emitter,
                        "getCurrentGeolocation",
                        this::class.java.simpleName
                    )
                }
            }
        }

    override fun getCityName(): Single<String> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData =
                    realmInstance
                        .where<UserAddressType>()
                        .findFirst()

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: UserAddressType? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null) {
                        val cityName = data.locality

                        if (cityName != null) {
                            emitter.onSuccess(cityName)
                        } else {
                            val part1 = context.getString(R.string.db_has_no_your_location_name)
                            val part2 = context.getString(R.string.get_geolocation_by_gps)

                            emitter.onError(Throwable("$part1. $part2"))
                        }
                    } else {
                        onDataIsNull(
                            emitter,
                            "getCityName",
                            this::class.java.simpleName
                        )
                    }
                } else if (!dataExistsInDB) {
                    val part1 = context.getString(R.string.db_has_nothing)
                    val part2 = context.getString(R.string.get_geolocation_by_gps)

                    emitter.onError(Throwable("$part1. $part2"))
                }
            }
        }

}



