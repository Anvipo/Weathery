package ru.mts.avpopo85.weathery.di.global

import dagger.Component
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherSubcomponent
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        RetrofitModule::class,
        NetworkModule::class,
        SchedulerManagerModule::class
    ]
)
interface AppComponent {
    fun provideYandexWeatherRepository(): WeatherRepository

    fun plus(yandexWeatherModule: YandexWeatherModule): YandexWeatherSubcomponent
}