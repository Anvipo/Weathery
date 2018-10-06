package ru.mts.avpopo85.weathery.data.db.implementation.realm.openWeatherMap

import android.content.Context
import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.data.utils.isFresh
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastResponseType
import java.util.*

class OWMForecastRealmService(private val context: Context) : IForecastDbService<OWMForecastListResponseType> {

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
                        if (data.isFresh || data.isNotFresh && !isConnectedToInternet) {
                            emitter.onSuccess(data)
                        } else if (isConnectedToInternet) {
                            val part1 = context.getString(R.string.db_has_outdated_data)

                            val part2 = context.getString(R.string.get_data_from_server)

                            emitter.onError(Throwable("$part1. $part2"))
                        }
                    } else {
                        onDataIsNull(
                            emitter,
                            "getForecastResponse",
                            this::class.java.simpleName
                        )
                    }
                } else if (!isConnectedToInternet) {
                    val part1 = context.getString(R.string.db_has_nothing)

                    val part2 = context.getString(R.string.you_have_no_internet_connection)

                    emitter.onError(Throwable("$part1. $part2"))
                } else if (isConnectedToInternet) {
                    val part1 = context.getString(R.string.db_has_nothing)

                    val part2 = context.getString(R.string.get_data_from_server)

                    emitter.onError(Throwable("$part1. $part2"))
                } else if (!dataExistsInDB) {
                    onProxyDataIsNull(
                        emitter,
                        "getForecastResponse",
                        this::class.java.simpleName
                    )
                }
            }
        }

    private val OWMForecastListResponseType.isFresh: Boolean
        get() {
            val unixtimeInMillis = this.first().dateInUnixtimeUTC

            return unixtimeInMillis.isFresh(
                OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS
            )
        }

    private val OWMForecastListResponseType.isNotFresh: Boolean
        get() {
            return !this.isFresh
        }

}