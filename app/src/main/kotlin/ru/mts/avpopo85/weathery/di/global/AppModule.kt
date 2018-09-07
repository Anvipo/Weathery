package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService
import ru.mts.avpopo85.weathery.data.repositories.YandexWeatherRepository
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import javax.inject.Singleton

@Module
class AppModule(context: Context) {
    private val context: Context = context.applicationContext

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = context

    @Provides
    @Singleton
    fun provideYandexWeatherRepository(yandexYandexWeatherRepository: YandexWeatherRepository): WeatherRepository {
        return yandexYandexWeatherRepository
    }

    @Provides
    @Singleton
    fun provideYandexWeatherRepositoryImpl(yandexWeatherApiService: YandexWeatherApiService): YandexWeatherRepository {
        return YandexWeatherRepository(yandexWeatherApiService)
    }
}
