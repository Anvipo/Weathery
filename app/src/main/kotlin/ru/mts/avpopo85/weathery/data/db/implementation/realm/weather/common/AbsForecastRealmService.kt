package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common

import android.content.Context
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.util.onDbHasNoWeatherResponse
import ru.mts.avpopo85.weathery.data.db.util.onDbHasOutdatedWeatherResponse
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse
import java.util.*


abstract class AbsForecastRealmService<T : IForecastRealmResponse>
constructor(private val context: Context) : IForecastDbService<T> {

    override fun saveForecastResponse(forecastResponseList: List<T>): Single<List<T>> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: List<T>? = null

                val nowInMillis: Long = Date().time

                forecastResponseList.map {
                    it.saveUnixTime = nowInMillis
                }

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(forecastResponseList)
                }

                val data: List<T> = realmInstance.copyFromRealm(proxyData!!)

                emitter.onSuccess(data)
            }
        }

    override fun getForecastResponse(isConnectedToInternet: Boolean): Single<List<T>> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData: RealmResults<T>? =
                    realmInstance
                        .where(responseClassType)
                        .findAll()

                val data: List<T> = realmInstance.copyFromRealm(proxyData!!)

                if (data.isNotEmpty()) {
                    if (data.isFresh || data.isNotFresh && !isConnectedToInternet) {
                        emitter.onSuccess(data)
                    } else if (isConnectedToInternet) {
                        context.onDbHasOutdatedWeatherResponse(emitter, isConnectedToInternet)
                    }
                } else {
                    context.onDbHasNoWeatherResponse(isConnectedToInternet, emitter)
                }
            }
        }


    abstract val responseClassType: Class<T>

    abstract val List<T>.isFresh: Boolean

    abstract val List<T>.isNotFresh: Boolean

}