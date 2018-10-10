package ru.mts.avpopo85.weathery.data.db.implementation.realm.location

import android.content.Context
import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.*
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

class LocationRealmService(private val context: Context) : ILocationDbService<UserAddressType> {

    override fun saveCurrentAddress(address: UserAddressType):
            Single<UserAddressType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: UserAddressType? = null

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(address)
                }

                val dataSavedInDB = proxyData != null

                if (dataSavedInDB) {
                    val data: UserAddressType? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null) {
                        emitter.onSuccess(data)
                    } else {
                        if (BuildConfig.DEBUG) {
                            val methodName =
                                object : Any() {}.javaClass.enclosingMethod?.name
                                    ?: "saveCurrentAddress"

                            onDataIsNull(emitter, methodName, this::class.java.simpleName)
                        }
                    }
                } else {
                    if (BuildConfig.DEBUG) {
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name
                                ?: "saveCurrentAddress"

                        onProxyDataIsNull(emitter, methodName, this::class.java.simpleName)
                    }
                }
            }
        }

    override fun getLastKnownAddress(
        isGpsProviderEnabled: Boolean,
        isNetworkProviderEnabled: Boolean,
        isConnectedToInternet: Boolean
    ): Single<UserAddressType> =
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
                        emitter.onSuccess(data)
                    } else {
                        if (BuildConfig.DEBUG) {
                            val methodName =
                                object : Any() {}.javaClass.enclosingMethod?.name
                                    ?: "getLastKnownAddress"

                            onDataIsNull(
                                emitter,
                                methodName,
                                this::class.java.simpleName
                            )
                        }
                    }
                } else if (!isConnectedToInternet && isGpsProviderEnabled) {
                    emitter.onError(InternetConnectionIsRequired(context.getString(R.string.internet_connection_required)))
                } else if (!isConnectedToInternet && !isGpsProviderEnabled) {
                    val message = context.getString(R.string.internet_conncetion_and_GPS_required)

                    emitter.onError(InternetConnectionIsRequired(message))
                } else if (!isGpsProviderEnabled) {
                    val part1 = context.getString(R.string.your_previous_location_is_unknown)
                    val part2 =
                        context.getString(R.string.find_out_your_current_location_in_one_of_the_suggested_ways)
                    val part3 = context.getString(R.string.for_example_by_gps)
                    val part4 = context.getString(R.string.you_must_enable_it)

                    emitter.onError(DBHasNothingAndGPSOffException("$part1. $part2, $part3. ($part4)"))
                } else if (isGpsProviderEnabled) {
                    val part1 = context.getString(R.string.your_previous_location_is_unknown)
                    val part2 =
                        context.getString(R.string.find_out_your_current_location_in_one_of_the_suggested_ways)
                    val part3 = context.getString(R.string.for_example_by_gps)
                    emitter.onError(DBHasNothingAndGPSOnException("$part1. $part2, $part3"))
                } else if (!dataExistsInDB) {
                    if (BuildConfig.DEBUG) {
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name
                                ?: "getLastKnownAddress"

                        onProxyDataIsNull(
                            emitter,
                            methodName,
                            this::class.java.simpleName
                        )
                    }
                }
            }
        }

    override fun getLastKnownCityName(): Single<String> =
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
                        if (BuildConfig.DEBUG) {
                            val methodName =
                                object : Any() {}.javaClass.enclosingMethod?.name
                                    ?: "getLastKnownCityName"

                            onDataIsNull(
                                emitter,
                                methodName,
                                this::class.java.simpleName
                            )
                        }
                    }
                } else if (!dataExistsInDB) {
                    val part1 = context.getString(R.string.db_has_nothing)
                    val part2 = context.getString(R.string.get_geolocation_by_gps)

                    emitter.onError(DBHasNothingAndGetGeolocationException("$part1. $part2"))
                }
            }
        }

}



