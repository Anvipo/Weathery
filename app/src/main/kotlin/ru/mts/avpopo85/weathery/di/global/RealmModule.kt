package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.yandexWeather.YWCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.yandexWeather.YWForecastRealmService
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.YWForecastListResponseType
import javax.inject.Singleton

@Module
class RealmModule(val context: Context) {

    init {
        Realm.init(context)
        Realm.setDefaultConfiguration(
            RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .name("realm")
                .build()
        )
    }

    @Provides
    @Singleton
    fun provideForecastDbService(): IForecastDbService<YWForecastListResponseType> =
        YWForecastRealmService()

    @Provides
    @Singleton
    fun provideCurrentWeatherDbService(): ICurrentWeatherDbService<YWCurrentWeatherResponseType> =
        YWCurrentWeatherRealmService()

}