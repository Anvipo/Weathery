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
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.*

class LocationRealmService(private val context: Context) : ILocationDbService<UserAddressType> {

    override fun saveAddress(address: UserAddressType):
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
                        //todo
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name ?: "saveAddress"

                        onDataIsNull(emitter, methodName, this::class.java.simpleName)
                    }
                } else {
                    val methodName =
                        object : Any() {}.javaClass.enclosingMethod?.name ?: "saveAddress"

                    onProxyDataIsNull(emitter, methodName, this::class.java.simpleName)
                }
            }
        }

    override fun getAddress(gpsIsEnabled: Boolean): Single<UserAddressType> =
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
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name ?: "getAddress"

                        onDataIsNull(
                            emitter,
                            methodName,
                            this::class.java.simpleName
                        )
                    }
                } else if (!gpsIsEnabled) {
                    val part1 = context.getString(R.string.your_previous_location_is_unknown)
                    val part2 =
                        context.getString(R.string.find_out_your_current_location_in_one_of_the_suggested_ways)
                    val part3 = context.getString(R.string.for_example_by_gps)
                    val part4 = context.getString(R.string.you_must_enable_it)

                    emitter.onError(DBHasNothingAndGPSOffException("$part1. $part2, $part3. ($part4)"))
                } else if (gpsIsEnabled) {
                    val part1 = context.getString(R.string.your_previous_location_is_unknown)
                    val part2 =
                        context.getString(R.string.find_out_your_current_location_in_one_of_the_suggested_ways)
                    val part3 = context.getString(R.string.for_example_by_gps)
                    emitter.onError(DBHasNothingAndGPSOffException("$part1. $part2, $part3"))
                } else if (!dataExistsInDB) {
                    val methodName =
                        object : Any() {}.javaClass.enclosingMethod?.name ?: "getAddress"

                    onProxyDataIsNull(
                        emitter,
                        methodName,
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

                            emitter.onError(DBHasNoFieldAndGetGeolocationException("$part1. $part2"))
                        }
                    } else {
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name ?: "getCityName"

                        onDataIsNull(
                            emitter,
                            methodName,
                            this::class.java.simpleName
                        )
                    }
                } else if (!dataExistsInDB) {
                    val part1 = context.getString(R.string.db_has_nothing)
                    val part2 = context.getString(R.string.get_geolocation_by_gps)

                    emitter.onError(DBHasNothingAndGetGeolocationException("$part1. $part2"))
                }
            }
        }

}



