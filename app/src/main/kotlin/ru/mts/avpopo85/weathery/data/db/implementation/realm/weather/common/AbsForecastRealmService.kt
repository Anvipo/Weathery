package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common

import android.content.Context
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.onDbHasNoWeatherResponse
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.onDbHasOutdatedWeatherResponse
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse
import java.util.Date


abstract class AbsForecastRealmService<T : IForecastRealmResponse>
constructor(private val context: Context) :
    AbsWeatherRealmService<T>(),
    IForecastDbService<T> {

    override fun saveForecastResponse(forecastResponseList: List<T>): Single<List<T>> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use {
                it.executeTransaction { realmInstance ->
                    clearDB(realmInstance)

                    val nowInMillis: Long = Date().time

                    forecastResponseList.map { forecast ->
                        forecast.saveUnixTime = nowInMillis
                    }

                    val proxyData: List<T> = realmInstance.copyToRealmOrUpdate(forecastResponseList)

                    val data: List<T> = realmInstance.copyFromRealm(proxyData)

                    emitter.onSuccess(data)
                }
            }
        }

    override fun getForecastResponse(isConnectedToInternet: Boolean): Single<List<T>> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData: RealmResults<T> =
                    realmInstance
                        .where(responseClassType)
                        .findAll()

                val data: List<T> = realmInstance.copyFromRealm(proxyData)

                if (data.isNotEmpty()) {
                    if (data.first().isFresh || data.first().isNotFresh && !isConnectedToInternet) {
                        emitter.onSuccess(data)
                    } else if (isConnectedToInternet) {
                        context.onDbHasOutdatedWeatherResponse(emitter, isConnectedToInternet)
                    }
                } else {
                    context.onDbHasNoWeatherResponse(isConnectedToInternet, emitter)
                }
            }
        }

    protected abstract val responseClassType: Class<T>

    private fun clearDB(realmInstance: Realm) {
        realmInstance.delete(responseClassType)
    }

}
