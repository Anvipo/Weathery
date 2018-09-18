package ru.mts.avpopo85.weathery.di.global

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService
import ru.mts.avpopo85.weathery.data.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.data.repositories.YandexWeatherRepository
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideWeatherRepository(
        yandexWeatherApiService: YandexWeatherApiService, networkManager: NetworkManager
    ): WeatherRepository = YandexWeatherRepository(yandexWeatherApiService, networkManager)

}
