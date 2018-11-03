package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.location.LocationRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.OWMCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.OWMForecastRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather.YWCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather.YWForecastRealmService
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMListItemResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType
import javax.inject.Singleton

@Module
class RealmModule(val context: Context) {

    init {
        Realm.init(context)

        val configuration = RealmConfiguration
            .Builder()
            .deleteRealmIfMigrationNeeded()
            .compactOnLaunch()
            .name("realm")
            .build()

        Realm.setDefaultConfiguration(configuration)

        Realm.compactRealm(configuration)
    }

    @Provides
    @Singleton
    fun provideYWForecastRealmService(): IForecastDbService<YWForecastResponseType> =
        YWForecastRealmService(context)

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
    fun provideOWMForecastRealmService(): IForecastDbService<OWMListItemResponseType> =
        OWMForecastRealmService(context)

    @Provides
    @Singleton
    fun provideLocationRealmService(sharedPreferences: SharedPreferences): ILocationDbService =
        LocationRealmService(context, sharedPreferences)

}
