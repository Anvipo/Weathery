package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.common

import android.content.Context
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.realm.Realm
import io.realm.RealmResults
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.db.util.onDbHasNoWeatherResponse
import ru.mts.avpopo85.weathery.data.db.util.onDbOutdatedWeatherData
import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherRealmResponse
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.WrongCityException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.common.onParameterIsNull


abstract class AbsCurrentWeatherRealmService<T : ICurrentWeatherRealmResponse>
constructor(
    private val context: Context,
    private val locationDbService: ILocationDbService<UserAddressType>
) : ICurrentWeatherDbService<T> {

    override fun saveCurrentWeatherResponse(currentWeatherResponse: T): Single<T> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: T? = null

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(currentWeatherResponse)
                }

                val currentWeatherResponseIsSavedInDB = proxyData != null

                when {
                    currentWeatherResponseIsSavedInDB -> onCurrentWeatherResponseIsSavedInDB(
                        realmInstance,
                        proxyData,
                        emitter
                    )
                    else -> onCurrentWeatherResponseIsNotSavedInDB(emitter)
                }
            }
        }

    override fun getCurrentWeatherResponse(isConnectedToInternet: Boolean): Single<T> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData =
                    realmInstance
                        .where(responseClassType)
                        .findAll()

                val dataExistsInDB = proxyData != null && proxyData.isNotEmpty()

                when {
                    dataExistsInDB -> onDataExistsInDB(realmInstance, proxyData!!, isConnectedToInternet, emitter)
                    isConnectedToInternet || !isConnectedToInternet -> context.onDbHasNoWeatherResponse(isConnectedToInternet, emitter)
                    !dataExistsInDB -> onDataDoesNotExistInDB(emitter)
                }
            }
        }

    private fun onDataExistsInDB(
        realmInstance: Realm,
        proxyData: RealmResults<T>,
        isConnectedToInternet: Boolean,
        emitter: SingleEmitter<T>
    ) {
        val currentWeatherResponse = proxyData.last()

        if (currentWeatherResponse != null) {
            val data: T? = realmInstance.copyFromRealm(currentWeatherResponse)

            when {
                data != null -> onNotNullData(data, isConnectedToInternet, emitter)
                else -> onNullData(emitter)
            }
        } else {
            onNullCurrentWeatherResponse(emitter)
        }
    }

    private fun onNotNullData(
        data: T,
        isConnectedToInternet: Boolean,
        emitter: SingleEmitter<T>
    ) {
        val currentCityName = locationDbService.getLastKnownCityName().blockingGet()

        val rightCity = data.cityName == currentCityName

        when {
            rightCity && (data.isFresh || (data.isNotFresh && !isConnectedToInternet)) -> emitter.onSuccess(data)
            !rightCity && !isConnectedToInternet -> onWrongCityAndIsNotConnectedToInternet(emitter)
            isConnectedToInternet -> context.onDbOutdatedWeatherData(emitter, isConnectedToInternet)
        }
    }

    private fun onNullData(emitter: SingleEmitter<T>) {
        if (BuildConfig.DEBUG) {
            val methodName =
                object : Any() {}.javaClass.enclosingMethod?.name
                    ?: "onNullData"

            onParameterIsNull(
                emitter,
                this::class.java.simpleName,
                methodName,
                "data"
            )
        }
    }

    private fun onNullCurrentWeatherResponse(emitter: SingleEmitter<T>) {
        if (BuildConfig.DEBUG) {
            val methodName =
                object : Any() {}.javaClass.enclosingMethod?.name
                    ?: "onNullCurrentWeatherResponse"

            onParameterIsNull(
                emitter,
                this::class.java.simpleName,
                methodName,
                "currentWeatherResponse"
            )
        }
    }

    private fun onCurrentWeatherResponseIsNotSavedInDB(emitter: SingleEmitter<T>) {
        if (BuildConfig.DEBUG) {
            val methodName =
                object : Any() {}.javaClass.enclosingMethod?.name
                    ?: "onCurrentWeatherResponseIsNotSavedInDB"

            onParameterIsNull(
                emitter,
                this::class.java.simpleName,
                methodName,
                "proxyData"
            )
        }
    }

    private fun onDataDoesNotExistInDB(emitter: SingleEmitter<T>) {
        if (BuildConfig.DEBUG) {
            val methodName =
                object : Any() {}.javaClass.enclosingMethod?.name
                    ?: "getCurrentWeatherResponse"

            onParameterIsNull(
                emitter,
                this::class.java.simpleName,
                methodName,
                "proxyData"
            )
        }
    }

    private fun onCurrentWeatherResponseIsSavedInDB(
        realmInstance: Realm,
        proxyData: T?,
        emitter: SingleEmitter<T>
    ) {
        val data: T? = realmInstance.copyFromRealm(proxyData!!)

        if (data != null) {
            emitter.onSuccess(data)
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "saveCurrentWeatherResponse"

                onParameterIsNull(
                    emitter,
                    this::class.java.simpleName,
                    methodName,
                    "data"
                )
            }
        }
    }

    private fun onWrongCityAndIsNotConnectedToInternet(emitter: SingleEmitter<T>) {
        val part1 =
            context.getString(R.string.your_current_location_has_changed_from_the_previous_one)
        val part2 = context.getString(R.string.you_have_no_internet_connection)

        val message = "$part1, $part2"

        val error = WrongCityException(message)

        emitter.onError(error)
    }

    abstract val responseClassType: Class<T>

    abstract val T.isFresh: Boolean

    abstract val T.isNotFresh: Boolean

}