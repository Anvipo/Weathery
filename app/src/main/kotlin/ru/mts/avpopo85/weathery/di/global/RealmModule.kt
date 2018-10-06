package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.location.LocationRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.openWeatherMap.OWMCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.openWeatherMap.OWMForecastRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.yandexWeather.YWCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.yandexWeather.YWForecastRealmService
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType
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
    fun provideYWForecastRealmService(): IForecastDbService<YWForecastListResponseType> =
        YWForecastRealmService()

    @Provides
    @Singleton
    fun provideYWCurrentWeatherRealmService(): ICurrentWeatherDbService<YWCurrentWeatherResponseType> =
        YWCurrentWeatherRealmService(context)

    @Provides
    @Singleton
    fun provideOWMCurrentWeatherRealmService(): ICurrentWeatherDbService<OWMCurrentWeatherResponseType> =
        OWMCurrentWeatherRealmService(context)

    @Provides
    @Singleton
    fun provideOWMForecastRealmService(): IForecastDbService<OWMForecastListResponseType> =
        OWMForecastRealmService(context)

    @Provides
    @Singleton
    fun provideLocationRealmService(): ILocationDbService<UserAddressType> =
        LocationRealmService(context)

}