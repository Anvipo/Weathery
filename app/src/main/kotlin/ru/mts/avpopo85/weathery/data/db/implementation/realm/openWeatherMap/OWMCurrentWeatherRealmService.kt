package ru.mts.avpopo85.weathery.data.db.implementation.realm.openWeatherMap

import android.content.Context
import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.data.utils.isFresh
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType

class OWMCurrentWeatherRealmService(private val context: Context) :
    ICurrentWeatherDbService<OWMCurrentWeatherResponseType> {

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

    override fun getCurrentWeatherResponse(isConnectedToInternet: Boolean): Single<OWMCurrentWeatherResponseType> =
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
                            val part1 = context.getString(R.string.db_has_outdated_data)

                            val part2 = context.getString(R.string.get_data_from_server)

                            emitter.onError(Throwable("$part1. $part2"))
                        }
                    } else {
                        onDataIsNull(
                            emitter,
                            "getCurrentWeatherResponse",
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



