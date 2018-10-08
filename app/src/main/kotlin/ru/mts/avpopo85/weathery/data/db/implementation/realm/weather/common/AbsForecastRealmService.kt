package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common

import android.content.Context
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.util.onDataIsNull
import ru.mts.avpopo85.weathery.data.db.util.onDbHasNothing
import ru.mts.avpopo85.weathery.data.db.util.onDbOutdatedData
import ru.mts.avpopo85.weathery.data.db.util.onProxyDataIsNull
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse
import java.util.*


abstract class AbsForecastRealmService<T : IForecastRealmResponse>
constructor(private val context: Context) : IForecastDbService<T> {

    abstract val responseClassType: Class<T>

    abstract val List<T>.isFresh: Boolean

    abstract val List<T>.isNotFresh: Boolean

    override fun saveForecastResponse(forecastResponseList: List<T>): Single<List<T>> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: List<T>? = null

                val nowInMillis = Date().time

                forecastResponseList.map {
                    it.saveUnixTime = nowInMillis
                }

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(forecastResponseList)
                }

                if (dataIsSavedInDB(proxyData)) {
                    val data: List<T>? = realmInstance.copyFromRealm(proxyData!!)

                    if (data != null && data.isNotEmpty())
                        emitter.onSuccess(data)
                    else {
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name
                                ?: "saveForecastResponse"

                        onDataIsNull(
                            emitter,
                            methodName,
                            this::class.java.simpleName
                        )
                    }
                } else {
                    val methodName =
                        object : Any() {}.javaClass.enclosingMethod?.name ?: "saveForecastResponse"

                    onProxyDataIsNull(
                        emitter,
                        methodName,
                        this::class.java.simpleName
                    )
                }
            }
        }

    override fun getForecastResponse(isConnectedToInternet: Boolean): Single<List<T>> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData: RealmResults<T>? =
                    realmInstance
                        .where(responseClassType)
                        .findAll()

                val dataExistsInDB = dataExistsInDB(proxyData)

                if (dataExistsInDB) {
                    val data: List<T>? = realmInstance.copyFromRealm(proxyData!!)

                    if (data != null && data.isNotEmpty()) {
                        if (data.isFresh || data.isNotFresh && !isConnectedToInternet) {
                            emitter.onSuccess(data)
                        } else if (isConnectedToInternet) {
                            context.onDbOutdatedData(emitter, isConnectedToInternet)
                        }
                    } else {
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name
                                ?: "getForecastResponse"

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
                        object : Any() {}.javaClass.enclosingMethod?.name ?: "getForecastResponse"

                    onProxyDataIsNull(
                        emitter,
                        methodName,
                        this::class.java.simpleName
                    )
                }
            }
        }

    private fun dataExistsInDB(proxyData: RealmResults<T>?) =
        proxyData != null && proxyData.isNotEmpty()

    private fun dataIsSavedInDB(proxyData: List<T>?) =
        proxyData != null && proxyData.isNotEmpty()

}