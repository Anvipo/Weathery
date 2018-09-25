package ru.mts.avpopo85.weathery.data.db.implementation.realm.openWeatherMap

import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.utils.isFreshThan
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType

//TODO пользователь выбирает интервал обновления
const val OWM_DEFAULT_CACHE_LIFETIME: Long = 1_800_000 //= 30 min

class OWMCurrentWeatherRealmService : ICurrentWeatherDbService<OWMCurrentWeatherResponseType> {

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
                        emitter.onError(Throwable("Не удалось сохранить данные в БД"))
                    }
                } else {
                    emitter.onError(Throwable("Не удалось сохранить данные в БД"))
                }
            }
        }

    @Suppress("SpellCheckingInspection")
    override fun getCurrentWeatherResponse(isConnectedToInternet: Boolean):
            Single<OWMCurrentWeatherResponseType> =
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
                        val unixtimeInMillis = data.timeOfDataCalculationUnixUTC * 1000L

                        val dataIsFresh = unixtimeInMillis.isFreshThan(OWM_DEFAULT_CACHE_LIFETIME)

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



