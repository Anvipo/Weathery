package ru.mts.avpopo85.weathery.data.db.implementation.realm.location

import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.data.utils.UserAddressType

class LocationRealmService : ILocationDbService<UserAddressType> {

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
                        //TODO проверять данные на устаревание
                        emitter.onSuccess(data)
                    } else {
                        onDataIsNull(
                            emitter,
                            "getCurrentGeolocation",
                            this::class.java.simpleName
                        )
                    }
                } else if (!gpsIsEnabled) {
                    //TODO
                    emitter.onError(Throwable("Вы не включили геолокацию и в БД ничего нет"))
                } else if (gpsIsEnabled) {
                    //TODO
                    emitter.onError(Throwable("В БД ничего нет. Получите данные с датчика"))
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
                            //TODO
                            emitter.onError(
                                Throwable(
                                    "В БД отсутствует название города. " +
                                            "Необходимо получить вашу текущую геопозицию"
                                )
                            )
                        }
                    } else {
                        onDataIsNull(
                            emitter,
                            "getCityName",
                            this::class.java.simpleName
                        )
                    }
                } /*else if (!isConnectedToInternet) {
                    emitter.onError(Throwable("Вы не подключены к интернету и в БД ничего нет"))
                } else if (isConnectedToInternet) {
                    emitter.onError(Throwable("В БД ничего нет. Получите данные с сервера"))
                } */ else if (!dataExistsInDB) {
                    emitter.onError(
                        Throwable(
                            "В БД ничего нет. " +
                                    "Необходимо получить вашу текущую геопозицию"
                        )
                    )
                    /*onProxyDataIsNull(
                        emitter,
                        "getCurrentWeatherResponse",
                        this::class.java.simpleName
                    )*/
                }
            }
        }

}



