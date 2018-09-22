package ru.mts.avpopo85.weathery.data.db.implementation

import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.ForecastDbService
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWForecastResponse
import ru.mts.avpopo85.weathery.utils.YWForecastResponseType

class ForecastRealmService : ForecastDbService<YWForecastResponseType> {

    override fun saveForecastResponse(currentWeatherResponse: YWForecastResponseType): Single<YWForecastResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val data: YWForecastResponseType? =
                    realmInstance.copyToRealmOrUpdate(currentWeatherResponse)

                if (data != null) {
                    emitter.onSuccess(data)
                } else {
                    emitter.onError(Throwable("Не удалось сохранить данные в БД"))
                }
            }
        }

    override fun getForecastResponse(isConnectedToInternet: Boolean): Single<YWForecastResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val data: RealmResults<YWForecastResponse>? =
                    realmInstance
                        .where<YWForecastResponse>()
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