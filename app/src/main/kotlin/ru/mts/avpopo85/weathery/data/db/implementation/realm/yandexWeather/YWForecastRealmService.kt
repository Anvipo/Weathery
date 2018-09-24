package ru.mts.avpopo85.weathery.data.db.implementation.realm.yandexWeather

import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.utils.isFreshThan
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType
import java.util.Date

class YWForecastRealmService : IForecastDbService<YWForecastListResponseType> {

    override fun saveForecastResponse(forecastResponseList: YWForecastListResponseType): Single<YWForecastListResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: YWForecastListResponseType? = null

                val nowInMillis = Date().time

                forecastResponseList.map {
                    it.saveUnixTime = nowInMillis
                }

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(forecastResponseList)
                }

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: YWForecastListResponseType? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null)
                        emitter.onSuccess(data)
                    else {
                        emitter.onError(Throwable("Не удалось сохранить данные в БД"))
                    }
                } else {
                    emitter.onError(Throwable("Не удалось сохранить данные в БД"))
                }
            }
        }

    override fun getForecastResponse(isConnectedToInternet: Boolean): Single<YWForecastListResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData =
                    realmInstance
                        .where<YWForecastResponseType>()
                        .findAll()

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB && proxyData!!.isNotEmpty()) {
                    val data: YWForecastListResponseType? =
                        realmInstance.copyFromRealm(proxyData)

                    if (data != null) {
                        val unixtimeInMillis = data.first().saveUnixTime

                        val dataIsFresh = unixtimeInMillis.isFreshThan(YW_DEFAULT_CACHE_LIFETIME)

                        if (dataIsFresh) {
                            emitter.onSuccess(data)
                        } else if (!isConnectedToInternet) {
                            emitter.onError(Throwable("Вы не подключены к интернету и в БД устаревшие данные"))
                        } /*else if (isConnectedToInternet) {
                            emitter.onError(Throwable("Данные устарели. Выполни запрос на сервер"))
                        }*/
                    } /*else if (isConnectedToInternet) {
                        emitter.onError(Throwable("В БД нет таких данных. Выполни запрос на сервер"))
                    }*/
                } else if (!isConnectedToInternet) {
                    emitter.onError(Throwable("Вы не подключены к интернету и в БД ничего нет"))
                } /*else if (isConnectedToInternet) {
                    emitter.onError(Throwable("В БД нет таких данных. Выполни запрос на сервер"))
                }*/
            }
        }

}