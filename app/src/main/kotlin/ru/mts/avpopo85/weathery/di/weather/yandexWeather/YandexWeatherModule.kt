package ru.mts.avpopo85.weathery.di.weather.yandexWeather

import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.global.scopes.YandexWeatherScope
import ru.mts.avpopo85.weathery.domain.global.repositories.YandexWeatherRepository
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather.YandexCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.YandexForecastPresenter

@Module
class YandexWeatherModule {
    @Provides
    @YandexWeatherScope
    fun provideInteractor(yandexWeatherRepository: YandexWeatherRepository): YandexWeatherInteractor {
        return YandexWeatherInteractor(yandexWeatherRepository)
    }

    @Provides
    @YandexWeatherScope
    fun provideYandexCurrentWeatherPresenter(
            yandexWeatherInteractor: YandexWeatherInteractor,
            schedulerManagerModule: SchedulerManagerModule
    ): YandexCurrentWeatherPresenter {
        return YandexCurrentWeatherPresenter(yandexWeatherInteractor, schedulerManagerModule)
    }

    @Provides
    @YandexWeatherScope
    fun provideYandexForecastPresenter(
            yandexWeatherInteractor: YandexWeatherInteractor,
            schedulerManagerModule: SchedulerManagerModule
    ): YandexForecastPresenter {
        return YandexForecastPresenter(yandexWeatherInteractor, schedulerManagerModule)
    }
}