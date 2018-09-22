package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.mts.avpopo85.weathery.data.db.base.CurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ForecastDbService
import ru.mts.avpopo85.weathery.data.db.implementation.CurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.ForecastRealmService
import ru.mts.avpopo85.weathery.utils.CurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.ForecastListResponseType
import javax.inject.Singleton

@Module
class RealmModule(val context: Context) {

    init {
        Realm.init(context)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().name("realm").build()
        )
    }

    /* @Provides
     @Singleton
     fun provideAccountManagerModule(context: Context) = RealmModule(context)*/

    @Provides
    @Singleton
    fun provideForecastDbService(): ForecastDbService<ForecastListResponseType> =
        ForecastRealmService()

    @Provides
    @Singleton
    fun provideCurrentWeatherDbService(): CurrentWeatherDbService<CurrentWeatherResponseType> =
        CurrentWeatherRealmService()

}