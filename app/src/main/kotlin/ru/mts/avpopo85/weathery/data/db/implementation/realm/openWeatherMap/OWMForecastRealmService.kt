package ru.mts.avpopo85.weathery.data.db.implementation.realm.openWeatherMap

import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.data.utils.isFreshThan
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastResponseType
import java.util.*

class OWMForecastRealmService : IForecastDbService<OWMForecastListResponseType> {

    override fun saveForecastResponse(forecastResponseList: OWMForecastListResponseType): Single<OWMForecastListResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: OWMForecastListResponseType? = null

                val nowInMillis = Date().time

                forecastResponseList.map {
                    it.saveUnixTime = nowInMillis
                }

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(forecastResponseList)
                }

                val dataExistsInDB = proxyData != null && proxyData!!.isNotEmpty()

                if (dataExistsInDB) {
                    val data: OWMForecastListResponseType? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null && data.isNotEmpty())
                        emitter.onSuccess(data)
                    else {
                        onDataIsNull(
                            emitter,
                            "saveForecastResponse",
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

    override fun getForecastResponse(isConnectedToInternet: Boolean): Single<OWMForecastListResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData =
                    realmInstance
                        .where<OWMForecastResponseType>()
                        .findAll()

                val dataExistsInDB = proxyData != null && proxyData.isNotEmpty()

                if (dataExistsInDB) {
                    val data: OWMForecastListResponseType? =
                        realmInstance.copyFromRealm(proxyData)

                    if (data != null && data.isNotEmpty()) {
                        @Suppress("SpellCheckingInspection")
                        val unixtimeInMillis = data.first().dateInUnixtimeUTC

                        val dataIsFresh =
                            unixtimeInMillis.isFreshThan(OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS)

                        if (dataIsFresh) {
                            emitter.onSuccess(data)
                        } else if (!isConnectedToInternet) {
                            emitter.onError(Throwable("Вы не подключены к интернету и в БД устаревшие данные"))
                        }
                    } else {
                        onDataIsNull(
                            emitter,
                            "getForecastResponse",
                            this::class.java.simpleName
                        )
                    }
                } else if (!dataExistsInDB) {
                    onProxyDataIsNull(
                        emitter,
                        "getForecastResponse",
                        this::class.java.simpleName
                    )
                } else if (!isConnectedToInternet) {
                    emitter.onError(Throwable("Вы не подключены к интернету и в БД ничего нет"))
                }
            }
        }

}