package ru.mts.avpopo85.weathery.data.db.implementation

import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.ForecastDbService
import ru.mts.avpopo85.weathery.utils.ForecastListResponseType
import ru.mts.avpopo85.weathery.utils.ForecastResponseType

class ForecastRealmService : ForecastDbService<ForecastListResponseType> {

    override fun saveForecastResponse(forecastListResponse: ForecastListResponseType): Single<ForecastListResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val data: ForecastListResponseType? =
                    realmInstance.copyToRealmOrUpdate(forecastListResponse)

                if (data != null) {
                    emitter.onSuccess(data)
                } else {
                    emitter.onError(Throwable("Не удалось сохранить данные в БД"))
                }
            }
        }

    override fun getForecastResponse(isConnectedToInternet: Boolean): Single<ForecastListResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val data: RealmResults<ForecastResponseType>? =
                    realmInstance
                        .where<ForecastResponseType>()
                        .findAll()

                if (data != null) {
                    emitter.onSuccess(data)
                } else {
                    if (isConnectedToInternet) {
                        emitter.onError(Throwable("В БД ничего нет"))
                    } else {
                        emitter.onError(Throwable("Вы не подключены к интернету и в БД ничего нет"))
                    }
                }
            }
        }

}