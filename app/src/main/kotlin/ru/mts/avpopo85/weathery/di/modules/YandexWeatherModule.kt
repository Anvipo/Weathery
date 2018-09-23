package ru.mts.avpopo85.weathery.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.scopes.YandexWeatherScope
import ru.mts.avpopo85.weathery.domain.interactor.base.ICurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.interactor.base.IForecastInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.yandexWeather.YWCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.interactor.implementation.yandexWeather.YWForecastInteractor
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.YWCurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.YWForecastMapper
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather.YWCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.YWForecastPresenter
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherType
import ru.mts.avpopo85.weathery.utils.YWForecastListResponseType
import ru.mts.avpopo85.weathery.utils.YWForecastListType

@Module
class YandexWeatherModule {

    @Provides
    @YandexWeatherScope
    fun provideYWCurrentWeatherPresenter(
        currentWeatherInteractor: ICurrentWeatherInteractor<YWCurrentWeatherType>,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): CurrentWeatherContract.Presenter<YWCurrentWeatherType> =
        YWCurrentWeatherPresenter(
            currentWeatherInteractor,
            schedulerManagerModule,
            context
        )

    @Provides
    @YandexWeatherScope
    fun provideYWCurrentWeatherInteractor(
        currentWeatherRepository: ICurrentWeatherRepository<YWCurrentWeatherResponseType>,
        currentWeatherMapper: ICurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType>
    ): ICurrentWeatherInteractor<YWCurrentWeatherType> =
        YWCurrentWeatherInteractor(
            currentWeatherRepository,
            currentWeatherMapper
        )

    @Provides
    @YandexWeatherScope
    fun provideYWCurrentWeatherMapper(context: Context):
            ICurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType> =
        YWCurrentWeatherMapper(context)

    @Provides
    @YandexWeatherScope
    fun provideYWForecastPresenter(
        forecastInteractor: IForecastInteractor<YWForecastListType>,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): ForecastContract.Presenter<YWForecastListType> =
        YWForecastPresenter(
            forecastInteractor,
            schedulerManagerModule,
            context
        )

    @Provides
    @YandexWeatherScope
    fun provideYWForecastInteractor(
        forecastRepository: IForecastRepository<YWForecastListResponseType>,
        forecastMapper: IForecastMapper<YWForecastListResponseType, YWForecastListType>
    ): IForecastInteractor<YWForecastListType> =
        YWForecastInteractor(
            forecastRepository,
            forecastMapper
        )

    @Provides
    @YandexWeatherScope
    fun provideYWForecastMapper(context: Context): IForecastMapper<YWForecastListResponseType, YWForecastListType> =
        YWForecastMapper(context)

}