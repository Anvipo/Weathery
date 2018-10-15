package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common

import android.content.Context
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.realm.Realm
import io.realm.RealmResults
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.onDbHasNoWeatherResponse
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils.onDbHasOutdatedWeatherResponse
import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherRealmResponse


abstract class AbsCurrentWeatherRealmService<T : ICurrentWeatherRealmResponse>
constructor(private val context: Context) :
    AbsWeatherRealmService<T>(),
    ICurrentWeatherDbService<T> {

    override fun saveCurrentWeatherResponse(currentWeatherResponse: T): Single<T> =
        Single.create { emitter ->
            Realm.getDefaultInstance().use { realmInstance ->
                var proxyData: T? = null

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(currentWeatherResponse)
                }

                val data: T = realmInstance.copyFromRealm(proxyData!!)

                emitter.onSuccess(data)
            }
        }

    override fun getCurrentWeatherResponse(isConnectedToInternet: Boolean): Single<T> =
        Single.create { emitter ->
            Realm.getDefaultInstance().use { realmInstance ->
                val proxyData: RealmResults<T> =
                    realmInstance
                        .where(responseClassType)
                        .findAll()

                val dataExistsInDB: Boolean = proxyData.isNotEmpty()

                if (dataExistsInDB) {
                    onDataExistsInDB(realmInstance, proxyData, isConnectedToInternet, emitter)
                } else {
                    context.onDbHasNoWeatherResponse(isConnectedToInternet, emitter)
                }
            }
        }

    private fun onDataExistsInDB(
        realmInstance: Realm,
        proxyData: RealmResults<T>,
        isConnectedToInternet: Boolean,
        emitter: SingleEmitter<T>
    ) {
        val currentWeatherResponse: T? = proxyData.last()

        if (currentWeatherResponse != null) {
            val data: T = realmInstance.copyFromRealm(currentWeatherResponse)

            onCurrentWeatherResponseExists(data, isConnectedToInternet, emitter)
        } else {
            context.onDbHasNoWeatherResponse(isConnectedToInternet, emitter)
        }
    }

    private fun onCurrentWeatherResponseExists(
        data: T,
        isConnectedToInternet: Boolean,
        emitter: SingleEmitter<T>
    ) {
        if (data.isFresh || (data.isNotFresh && !isConnectedToInternet)) {
            emitter.onSuccess(data)
        } else if (isConnectedToInternet) {
            context.onDbHasOutdatedWeatherResponse(emitter, isConnectedToInternet)
        }
    }

    protected abstract val responseClassType: Class<T>

}
