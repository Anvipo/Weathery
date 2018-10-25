package ru.mts.avpopo85.weathery.data.db.implementation.realm.location

import android.content.Context
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.delete
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasNothingAndGetGeolocationException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import javax.inject.Inject

class LocationRealmService
@Inject constructor(private val context: Context) : ILocationDbService<UserAddressType> {

    override fun saveCurrentAddress(address: UserAddressType): Single<UserAddressType> =
        Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransaction { realmInstance ->
                    clearDB(realmInstance)

                    val proxyData: UserAddressType = realmInstance.copyToRealmOrUpdate(address)

                    val currentAddress: UserAddressType = realmInstance.copyFromRealm(proxyData)

                    emitter.onSuccess(currentAddress)
                }
            }
        }

    override fun getLastKnownAddress(
        isGpsProviderEnabled: Boolean,
        isNetworkProviderEnabled: Boolean,
        isConnectedToInternet: Boolean
    ): Single<UserAddressType> =
        Single.create { emitter ->
            Realm.getDefaultInstance().use { realmInstance ->
                val proxyData: RealmResults<UserAddressType> =
                    realmInstance
                        .where<UserAddressType>()
                        .findAll()

                val dataExistsInDB: Boolean = proxyData.isNotEmpty()

                if (dataExistsInDB) {
                    onDataExistsInDB(proxyData, realmInstance, emitter)
                } else {
                    context.onDbHasNoCurrentAddress(emitter)
                }
            }
        }

    override fun getLastKnownCityName(): Single<String> =
        Single.create { emitter ->
            Realm.getDefaultInstance().use { realmInstance ->
                val proxyData: RealmResults<UserAddressType> =
                    realmInstance
                        .where<UserAddressType>()
                        .findAll()

                val citiesExistInDB: Boolean = proxyData.isNotEmpty()

                if (citiesExistInDB) {
                    onCitiesExistInDBInDB(realmInstance, proxyData, emitter)
                } else if (!citiesExistInDB) {
                    context.onDbHasNoCurrentAddress(emitter)
                }
            }
        }

    private fun clearDB(realmInstance: Realm) {
        realmInstance.delete<UserAddressType>()
    }

    private fun onDataExistsInDB(
        proxyData: RealmResults<UserAddressType>,
        realmInstance: Realm,
        emitter: SingleEmitter<UserAddressType>
    ) {
        val lastSaveDate = proxyData.max("saveDate")

        val proxyLastKnownAddress: UserAddressType? = proxyData.find { it.saveDate == lastSaveDate }

        if (proxyLastKnownAddress != null) {
            val lastKnownAddress: UserAddressType =
                realmInstance.copyFromRealm(proxyLastKnownAddress)

            emitter.onSuccess(lastKnownAddress)
        } else {
            context.onDbHasNoCurrentAddress(emitter)
        }
    }

    private fun onCitiesExistInDBInDB(
        realmInstance: Realm,
        proxyData: RealmResults<UserAddressType>,
        emitter: SingleEmitter<String>
    ) {
        val proxyLastKnownAddress: UserAddressType? = proxyData.last()

        if (proxyLastKnownAddress != null) {
            val lastKnownAddress: UserAddressType =
                realmInstance.copyFromRealm(proxyLastKnownAddress)

            val cityName: String? = lastKnownAddress.locality

            if (cityName != null) {
                emitter.onSuccess(cityName)
            } else {
                context.onDbHasNoCurrentAddress(emitter)
            }
        } else {
            context.onDbHasNoCurrentAddress(emitter)
        }
    }

    private fun <T> Context.onDbHasNoCurrentAddress(emitter: SingleEmitter<T>) {
        val part1 = getString(R.string.no_previous_address_data)

        val part2 = getString(R.string.find_out_your_current_location_in_one_of_the_suggested_ways)

        val message = "$part1. $part2"

        val error = DBHasNothingAndGetGeolocationException(message)

        emitter.onError(error)
    }

}
