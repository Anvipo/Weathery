package ru.mts.avpopo85.weathery.data.db.implementation.realm.openWeatherMap

import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.data.utils.isFresh
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType

class OWMCurrentWeatherRealmService : ICurrentWeatherDbService<OWMCurrentWeatherResponseType> {

    override fun saveCurrentWeatherResponse(currentWeatherResponse: OWMCurrentWeatherResponseType):
            Single<OWMCurrentWeatherResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: OWMCurrentWeatherResponseType? = null

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(currentWeatherResponse)
                }

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: OWMCurrentWeatherResponseType? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null)
                        emitter.onSuccess(data)
                    else {
                        onDataIsNull(
                            emitter,
                            "saveCurrentWeatherResponse",
                            this::class.java.simpleName
                        )
                    }
                } else {
                    onProxyDataIsNull(
                        emitter,
                        "saveCurrentWeatherResponse",
                        this::class.java.simpleName
                    )
                }
            }
        }

    override fun getCurrentWeatherResponse(isConnectedToInternet: Boolean):
            Single<OWMCurrentWeatherResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData =
                    realmInstance
                        .where<OWMCurrentWeatherResponseType>()
                        .findFirst()

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: OWMCurrentWeatherResponseType? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null) {
                        if (data.isFresh || data.isNotFresh && !isConnectedToInternet) {
                            emitter.onSuccess(data)
                        } else if (isConnectedToInternet) {
                            //TODO
                            emitter.onError(Throwable("В БД устаревшие данные. Получите данные с сервера"))
                        }
                    } else {
                        onDataIsNull(
                            emitter,
                            "getCurrentWeatherResponse",
                            this::class.java.simpleName
                        )
                    }
                } else if (!isConnectedToInternet) {
                    //TODO
                    emitter.onError(Throwable("Вы не подключены к интернету и в БД ничего нет"))
                } else if (isConnectedToInternet) {
                    //TODO
                    emitter.onError(Throwable("В БД ничего нет. Получите данные с сервера"))
                } else if (!dataExistsInDB) {
                    onProxyDataIsNull(
                        emitter,
                        "getCurrentWeatherResponse",
                        this::class.java.simpleName
                    )
                }
            }
        }

    private val OWMCurrentWeatherResponseType.isFresh: Boolean
        get() {
            val unixtimeInMillis = this.timeOfDataCalculationUnixUTC * 1000

            return unixtimeInMillis.isFresh(
                OWM_CURRENT_WEATHER_CACHE_LIFETIME_IN_MS
            )
        }

    private val OWMCurrentWeatherResponseType.isNotFresh: Boolean
        get() {
            return !this.isFresh
        }

}



