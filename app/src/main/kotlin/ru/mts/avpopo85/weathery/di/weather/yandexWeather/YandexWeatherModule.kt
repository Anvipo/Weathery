package ru.mts.avpopo85.weathery.di.weather.yandexWeather

import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.global.scopes.YandexWeatherScope
import ru.mts.avpopo85.weathery.domain.global.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather.YandexCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast.YandexForecastInteractor
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather.YandexCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.YandexForecastPresenter

@Module
class YandexWeatherModule {
    @Provides
    @YandexWeatherScope
    fun provideYandexCurrentWeatherInteractor(weatherRepository: WeatherRepository): YandexCurrentWeatherInteractor {
        return YandexCurrentWeatherInteractor(
            weatherRepository
        )
    }

    @Provides
    @YandexWeatherScope
    fun provideYandexCurrentWeatherPresenter(
        yandexCurrentWeatherInteractor: YandexCurrentWeatherInteractor,
        schedulerManagerModule: SchedulerManagerModule
    ): YandexCurrentWeatherPresenter {
        return YandexCurrentWeatherPresenter(yandexCurrentWeatherInteractor, schedulerManagerModule)
    }

    @Provides
    @YandexWeatherScope
    fun provideYandexForecastInteractor(weatherRepository: WeatherRepository): YandexForecastInteractor {
        return YandexForecastInteractor(
            weatherRepository
        )
    }

    @Provides
    @YandexWeatherScope
    fun provideYandexForecastPresenter(
        yandexForecastInteractor: YandexForecastInteractor,
        schedulerManagerModule: SchedulerManagerModule
    ): YandexForecastPresenter {
        return YandexForecastPresenter(yandexForecastInteractor, schedulerManagerModule)
    }
}