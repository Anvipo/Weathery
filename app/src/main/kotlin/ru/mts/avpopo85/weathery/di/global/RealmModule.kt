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
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.OWMCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.OWMForecastRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather.YWCurrentWeatherRealmService
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather.YWForecastRealmService
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType
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
    fun provideYWForecastRealmService(): IForecastDbService<YWForecastResponseType> =
        YWForecastRealmService(context)

    @Provides
    @Singleton
    fun provideYWCurrentWeatherRealmService(locationDbService: ILocationDbService<UserAddressType>): ICurrentWeatherDbService<YWCurrentWeatherResponseType> =
        YWCurrentWeatherRealmService(context, locationDbService)

    @Provides
    @Singleton
    fun provideOWMCurrentWeatherRealmService(locationDbService: ILocationDbService<UserAddressType>): ICurrentWeatherDbService<OWMCurrentWeatherResponseType> =
        OWMCurrentWeatherRealmService(context, locationDbService)

    @Provides
    @Singleton
    fun provideOWMForecastRealmService(): IForecastDbService<OWMForecastResponseType> =
        OWMForecastRealmService(context)

    @Provides
    @Singleton
    fun provideLocationRealmService(): ILocationDbService<UserAddressType> =
        LocationRealmService(context)

}