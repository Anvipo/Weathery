package ru.mts.avpopo85.weathery.data.db.implementation.realm.yandexWeather

import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.data.utils.isFresh
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType

const val YW_DEFAULT_CACHE_LIFETIME: Long = 7_200_000 //= 2 hours

class YWCurrentWeatherRealmService : ICurrentWeatherDbService<YWCurrentWeatherResponseType> {
    //TODO починить как в owm
    override fun saveCurrentWeatherResponse(currentWeatherResponse: YWCurrentWeatherResponseType): Single<YWCurrentWeatherResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: YWCurrentWeatherResponseType? = null

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(currentWeatherResponse)
                }

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: YWCurrentWeatherResponseType? =
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

    override fun getCurrentWeatherResponse(isConnectedToInternet: Boolean): Single<YWCurrentWeatherResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData =
                    realmInstance
                        .where<YWCurrentWeatherResponseType>()
                        .findFirst()

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: YWCurrentWeatherResponseType? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null) {
                        val unixtimeInMillis = data.observationUnixTime * 1000L

                        val dataIsFresh = unixtimeInMillis.isFresh(YW_DEFAULT_CACHE_LIFETIME)

                        if (dataIsFresh) {
                            emitter.onSuccess(data)
                        } else if (!isConnectedToInternet) {
                            emitter.onError(Throwable("Вы не подключены к интернету и в БД устаревшие данные"))
                        }
                    } else {
                        onDataIsNull(
                            emitter,
                            "getCurrentWeatherResponse",
                            this::class.java.simpleName
                        )
                    }
                } else if (!isConnectedToInternet) {
                    emitter.onError(Throwable("Вы не подключены к интернету и в БД ничего нет"))
                } else if (isConnectedToInternet) {
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

}