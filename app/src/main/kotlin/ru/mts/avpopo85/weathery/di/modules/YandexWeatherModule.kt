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
import ru.mts.avpopo85.weathery.utils.CurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.CurrentWeatherType
import ru.mts.avpopo85.weathery.utils.ForecastListResponseType
import ru.mts.avpopo85.weathery.utils.ForecastListType

@Module
class YandexWeatherModule {

    @Provides
    @YandexWeatherScope
    fun provideYWCurrentWeatherPresenter(
        yandexCurrentWeatherInteractor: CurrentWeatherInteractor<CurrentWeatherType>,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): CurrentWeatherContract.Presenter =
        YWCurrentWeatherPresenter(
            yandexCurrentWeatherInteractor,
            schedulerManagerModule,
            context
        )

    @Provides
    @YandexWeatherScope
    fun provideYWCurrentWeatherInteractor(
        currentWeatherRepository: CurrentWeatherRepository<CurrentWeatherResponseType>,
        currentWeatherMapper: CurrentWeatherMapper<CurrentWeatherResponseType, CurrentWeatherType>
    ): CurrentWeatherInteractor<CurrentWeatherType> =
        YWCurrentWeatherInteractor(
            currentWeatherRepository,
            currentWeatherMapper
        )

    @Provides
    @YandexWeatherScope
    fun provideYWCurrentWeatherMapper(context: Context):
            CurrentWeatherMapper<CurrentWeatherResponseType, CurrentWeatherType> =
        YWCurrentWeatherMapper(context)

    @Provides
    @YandexWeatherScope
    fun provideYWForecastPresenter(
        forecastListInteractor: ForecastInteractor<ForecastListType>,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): ForecastContract.Presenter =
        YWForecastPresenter(
            forecastListInteractor,
            schedulerManagerModule,
            context
        )

    @Provides
    @YandexWeatherScope
    fun provideYWForecastInteractor(
        forecastListRepository: ForecastRepository<ForecastListResponseType>,
        forecastListListMapper: ForecastMapper<ForecastListResponseType, ForecastListType>
    ): ForecastInteractor<ForecastListType> =
        YWForecastInteractor(
            forecastListRepository,
            forecastListListMapper
        )

    @Provides
    @YandexWeatherScope
    fun provideYWForecastMapper(context: Context): ForecastMapper<ForecastListResponseType, ForecastListType> =
        YWForecastMapper(context)

}