package ru.mts.avpopo85.weathery.di.global

import dagger.Component
import ru.mts.avpopo85.weathery.di.weather.YandexWeatherModule
import ru.mts.avpopo85.weathery.di.weather.YandexWeatherSubcomponent
import ru.mts.avpopo85.weathery.domain.global.repositories.YandexWeatherRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, SchedulerManagerModule::class])
interface AppComponent {
    fun provideYandexWeatherRepository(): YandexWeatherRepository

    fun plus(yandexWeatherModule: YandexWeatherModule): YandexWeatherSubcomponent
}