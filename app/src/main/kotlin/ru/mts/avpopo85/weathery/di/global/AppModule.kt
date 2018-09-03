package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService
import ru.mts.avpopo85.weathery.data.repositories.YandexWeatherRepositoryImpl
import ru.mts.avpopo85.weathery.domain.global.repositories.YandexWeatherRepository
import javax.inject.Singleton

@Module
class AppModule(context: Context) {
    private val context: Context = context.applicationContext

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = context

    @Provides
    @Singleton
    fun provideYandexWeatherRepository(yandexWeatherRepository: YandexWeatherRepositoryImpl): YandexWeatherRepository {
        return yandexWeatherRepository
    }

    @Provides
    @Singleton
    fun provideYandexWeatherRepositoryImpl(yandexWeatherApiService: YandexWeatherApiService): YandexWeatherRepositoryImpl {
        return YandexWeatherRepositoryImpl(yandexWeatherApiService)
    }
}
