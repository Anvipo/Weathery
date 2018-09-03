package ru.mts.avpopo85.weathery.di.weather

import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.global.scopes.YandexWeatherScope
import ru.mts.avpopo85.weathery.domain.global.repositories.YandexWeatherRepository
import ru.mts.avpopo85.weathery.domain.weather.yandex.YandexWeatherInteractor
import ru.mts.avpopo85.weathery.presentation.weather.yandex.YandexWeatherPresenter

@Module
class YandexWeatherModule {
    @Provides
    @YandexWeatherScope
    fun provideInteractor(yandexWeatherRepository: YandexWeatherRepository): YandexWeatherInteractor {
        return YandexWeatherInteractor(yandexWeatherRepository)
    }


    @Provides
    @YandexWeatherScope
    fun providePresenter(
            yandexWeatherInteractor: YandexWeatherInteractor,
            schedulerManagerModule: SchedulerManagerModule
    ): YandexWeatherPresenter {
        return YandexWeatherPresenter(yandexWeatherInteractor, schedulerManagerModule)
    }
}