package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common

import android.content.Context
import io.reactivex.Single
import io.realm.Realm
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onDbHasNothing
import ru.mts.avpopo85.weathery.data.db.util.onDbOutdatedData
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherRealmResponse


abstract class AbsCurrentWeatherRealmService<T : ICurrentWeatherRealmResponse>
constructor(private val context: Context) : ICurrentWeatherDbService<T> {

    override fun saveCurrentWeatherResponse(currentWeatherResponse: T): Single<T> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: T? = null

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(currentWeatherResponse)
                }

                val dataSavedInDB = proxyData != null

                if (dataSavedInDB) {
                    val data: T? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null) {
                        emitter.onSuccess(data)
                    } else {
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name
                                ?: "saveCurrentWeatherResponse"

                        onDataIsNull(
                            emitter,
                            methodName,
                            this::javaClass.name
                        )
                    }
                } else {
                    val methodName =
                        object : Any() {}.javaClass.enclosingMethod?.name
                            ?: "saveCurrentWeatherResponse"

                    onProxyDataIsNull(
                        emitter,
                        methodName,
                        this::class.java.simpleName
                    )
                }
            }
        }

    override fun getCurrentWeatherResponse(isConnectedToInternet: Boolean): Single<T> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData =
                    realmInstance
                        .where(responseClassType)
                        .findFirst()

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: T? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null) {
                        if (data.isFresh || data.isNotFresh && !isConnectedToInternet) {
                            emitter.onSuccess(data)
                        } else if (isConnectedToInternet) {
                            context.onDbOutdatedData(emitter, isConnectedToInternet)
                        }
                    } else {
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name
                                ?: "getCurrentWeatherResponse"

                        onDataIsNull(
                            emitter,
                            methodName,
                            this::class.java.simpleName
                        )
                    }
                } else if (isConnectedToInternet || !isConnectedToInternet) {
                    context.onDbHasNothing(isConnectedToInternet, emitter)
                } else if (!dataExistsInDB) {
                    val methodName =
                        object : Any() {}.javaClass.enclosingMethod?.name
                            ?: "getCurrentWeatherResponse"

                    onProxyDataIsNull(
                        emitter,
                        methodName,
                        this::class.java.simpleName
                    )
                }
            }
        }

    abstract val responseClassType: Class<T>

    abstract val T.isFresh: Boolean

    abstract val T.isNotFresh: Boolean

}