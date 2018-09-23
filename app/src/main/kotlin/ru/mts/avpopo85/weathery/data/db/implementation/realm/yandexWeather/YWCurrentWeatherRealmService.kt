package ru.mts.avpopo85.weathery.data.db.implementation.realm.yandexWeather

import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.utils.isFreshThan
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType

const val YW_DEFAULT_CACHE_LIFETIME = 7_200_000L //= 2 hours

class YWCurrentWeatherRealmService : ICurrentWeatherDbService<YWCurrentWeatherResponseType> {

    override fun saveCurrentWeatherResponse(currentWeatherResponse: YWCurrentWeatherResponseType): Single<YWCurrentWeatherResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                var proxyData: YWCurrentWeatherResponseType? = null

                realmInstance.executeTransaction {
                    proxyData = realmInstance.copyToRealmOrUpdate(currentWeatherResponse)
                }

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: YWCurrentWeatherResponseType? =
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

    @Suppress("SpellCheckingInspection")
    override fun getCurrentWeatherResponse(isConnectedToInternet: Boolean): Single<YWCurrentWeatherResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val proxyData =
                    realmInstance
                        .where<YWCurrentWeatherResponseType>()
                        .findFirst()

                val dataExistsInDB = proxyData != null

                if (dataExistsInDB) {
                    val data: YWCurrentWeatherResponseType? =
                        realmInstance.copyFromRealm(proxyData!!)

                    if (data != null) {
                        val unixtimeInMillis = data.observationUnixTime * 1000L

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