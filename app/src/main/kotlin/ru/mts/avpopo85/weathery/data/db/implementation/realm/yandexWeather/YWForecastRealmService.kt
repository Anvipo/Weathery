package ru.mts.avpopo85.weathery.data.db.implementation.realm.yandexWeather

import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.utils.YWForecastListResponseType
import ru.mts.avpopo85.weathery.utils.YWForecastResponseType

class YWForecastRealmService : IForecastDbService<YWForecastListResponseType> {

    override fun saveForecastResponse(forecastResponseList: YWForecastListResponseType): Single<YWForecastListResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val data: YWForecastListResponseType? =
                    realmInstance.copyToRealmOrUpdate(forecastResponseList)

                if (data != null) {
                    emitter.onSuccess(data)
                } else {
                    emitter.onError(Throwable("Не удалось сохранить данные в БД"))
                }
            }
        }

    override fun getForecastResponse(isConnectedToInternet: Boolean): Single<YWForecastListResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val data: RealmResults<YWForecastResponseType>? =
                    realmInstance
                        .where<YWForecastResponseType>()
                        .findAll()

                if (data != null && data.isNotEmpty()) {
                    emitter.onSuccess(data)
                } else {
                    if (isConnectedToInternet) {
                        //как сюда вообще зайдёт?
                        emitter.onError(Throwable("В БД ничего нет"))
                    } else {
                        emitter.onError(Throwable("Вы не подключены к интернету и в БД ничего нет"))
                    }
                }
            }
        }

}