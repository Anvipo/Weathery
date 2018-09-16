package ru.mts.avpopo85.weathery.di.global

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.network.NetManager
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService
import ru.mts.avpopo85.weathery.data.repositories.YandexWeatherRepository
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Provides
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideWeatherRepository(yandexYandexWeatherRepository: YandexWeatherRepository): WeatherRepository =
        yandexYandexWeatherRepository

    @Provides
    @Singleton
    fun provideYandexWeatherRepositoryImpl(
        yandexWeatherApiService: YandexWeatherApiService,
        netManager: NetManager
    ): YandexWeatherRepository =
        YandexWeatherRepository(yandexWeatherApiService, netManager)
}
