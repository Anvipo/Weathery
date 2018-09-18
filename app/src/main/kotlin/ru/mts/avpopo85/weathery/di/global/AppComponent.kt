package ru.mts.avpopo85.weathery.di.global

import dagger.Component
import ru.mts.avpopo85.weathery.data.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherSubcomponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        RetrofitModule::class, NetworkModule::class, SchedulerManagerModule::class, DbModule::class
    ]
)
interface AppComponent {

    fun provideYandexWeatherRepository(): WeatherRepository

    fun plus(yandexWeatherModule: YandexWeatherModule): YandexWeatherSubcomponent

}