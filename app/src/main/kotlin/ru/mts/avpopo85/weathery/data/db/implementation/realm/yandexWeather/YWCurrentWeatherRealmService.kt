package ru.mts.avpopo85.weathery.data.db.implementation.realm.yandexWeather

import io.reactivex.Single
import io.realm.Realm
import io.realm.kotlin.where
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType

class YWCurrentWeatherRealmService : ICurrentWeatherDbService<YWCurrentWeatherResponseType> {

    override fun saveCurrentWeatherResponse(currentWeatherResponse: YWCurrentWeatherResponseType): Single<YWCurrentWeatherResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val data: YWCurrentWeatherResponseType? =
                    realmInstance.copyToRealmOrUpdate(currentWeatherResponse)

                if (data != null) {
                    emitter.onSuccess(data)
                } else {
                    emitter.onError(Throwable("Не удалось сохранить данные в БД"))
                }
            }
        }

    override fun getCurrentWeatherResponse(isConnectedToInternet: Boolean): Single<YWCurrentWeatherResponseType> =
        Single.create { emitter ->
            Realm.getDefaultInstance()?.use { realmInstance ->
                val data =
                    realmInstance
                        .where<YWCurrentWeatherResponseType>()
                        .findFirst()

                if (data != null) {
                    emitter.onSuccess(realmInstance.copyFromRealm(data)!!)
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