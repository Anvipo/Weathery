package ru.mts.avpopo85.weathery.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.YandexWeatherScope
import ru.mts.avpopo85.weathery.domain.interactor.base.CurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.interactor.base.ForecastInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.YWCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.YWForecastInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.CurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.base.ForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.YWCurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.YWForecastMapper
import ru.mts.avpopo85.weathery.domain.repository.CurrentWeatherRepository
import ru.mts.avpopo85.weathery.domain.repository.ForecastRepository
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather.YWCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.implementation.YWForecastPresenter
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherType
import ru.mts.avpopo85.weathery.utils.YWForecastResponseType
import ru.mts.avpopo85.weathery.utils.YWForecastType

@Module
class YandexWeatherModule {

    @Provides
    @YandexWeatherScope
    fun provideYWCurrentWeatherPresenter(
        yandexYWCurrentWeatherInteractor: CurrentWeatherInteractor<YWCurrentWeatherType>,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): CurrentWeatherContract.Presenter =
        YWCurrentWeatherPresenter(
            yandexYWCurrentWeatherInteractor,
            schedulerManagerModule,
            context
        )

    @Provides
    @YandexWeatherScope
    fun provideYWCurrentWeatherInteractor(
        currentWeatherRepository: CurrentWeatherRepository<YWCurrentWeatherResponseType>,
        currentWeatherMapper: CurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType>
    ): CurrentWeatherInteractor<YWCurrentWeatherType> =
        YWCurrentWeatherInteractor(
            currentWeatherRepository,
            currentWeatherMapper
        )

    @Provides
    @YandexWeatherScope
    fun provideYWCurrentWeatherMapper(context: Context):
            CurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType> =
        YWCurrentWeatherMapper(context)

    @Provides
    @YandexWeatherScope
    fun provideYWForecastPresenter(
        forecastInteractor: ForecastInteractor<YWForecastType>,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): ForecastContract.Presenter =
        YWForecastPresenter(
            forecastInteractor,
            schedulerManagerModule,
            context
        )

    @Provides
    @YandexWeatherScope
    fun provideYWForecastInteractor(
        forecastRepository: ForecastRepository<YWForecastResponseType>,
        forecastMapper: ForecastMapper<YWForecastResponseType, YWForecastType>
    ): ForecastInteractor<YWForecastType> =
        YWForecastInteractor(
            forecastRepository,
            forecastMapper
        )

    @Provides
    @YandexWeatherScope
    fun provideYWForecastMapper(context: Context): ForecastMapper<YWForecastResponseType, YWForecastType> =
        YWForecastMapper(context)

}