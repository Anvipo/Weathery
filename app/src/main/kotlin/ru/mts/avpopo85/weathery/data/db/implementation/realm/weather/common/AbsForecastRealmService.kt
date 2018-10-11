package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common

import android.content.Context
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.util.onDbHasNoWeatherResponse
import ru.mts.avpopo85.weathery.data.db.util.onDbOutdatedWeatherData
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse
import ru.mts.avpopo85.weathery.utils.common.onParameterIsNull
import java.util.*


abstract class AbsForecastRealmService<T : IForecastRealmResponse>
constructor(private val context: Context) : IForecastDbService<T> {

    //todo
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
                        if (BuildConfig.DEBUG) {
                            val methodName =
                                object : Any() {}.javaClass.enclosingMethod?.name
                                    ?: "saveForecastResponse"

                            onParameterIsNull(
                                emitter,
                                this::class.java.simpleName,
                                methodName,
                                "data"
                            )
                        }
                    }
                } else {
                    if (BuildConfig.DEBUG) {
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name
                                ?: "saveForecastResponse"

                        onParameterIsNull(
                            emitter,
                            this::class.java.simpleName,
                            methodName,
                            "proxyData"
                        )
                    }
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
                            context.onDbOutdatedWeatherData(emitter, isConnectedToInternet)
                        }
                    } else {
                        if (BuildConfig.DEBUG) {
                            val methodName =
                                object : Any() {}.javaClass.enclosingMethod?.name
                                    ?: "getForecastResponse"

                            onParameterIsNull(
                                emitter,
                                this::class.java.simpleName,
                                methodName,
                                "data"
                            )
                        }
                    }
                } else if (isConnectedToInternet || !isConnectedToInternet) {
                    context.onDbHasNoWeatherResponse(isConnectedToInternet, emitter)
                } else if (!dataExistsInDB) {
                    if (BuildConfig.DEBUG) {
                        val methodName =
                            object : Any() {}.javaClass.enclosingMethod?.name
                                ?: "getForecastResponse"

                        onParameterIsNull(
                            emitter,
                            this::class.java.simpleName,
                            methodName,
                            "proxyData"
                        )
                    }
                }
            }
        }

    abstract val responseClassType: Class<T>

    abstract val List<T>.isFresh: Boolean

    abstract val List<T>.isNotFresh: Boolean

    private fun dataExistsInDB(proxyData: RealmResults<T>?) =
        proxyData != null && proxyData.isNotEmpty()

    private fun dataIsSavedInDB(proxyData: List<T>?) =
        proxyData != null && proxyData.isNotEmpty()

}