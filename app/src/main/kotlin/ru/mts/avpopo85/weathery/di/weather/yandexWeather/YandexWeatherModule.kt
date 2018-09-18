package ru.mts.avpopo85.weathery.di.weather.yandexWeather

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.repositories.WeatherRepository
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.di.global.scopes.YandexWeatherScope
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather.YandexCurrentWeatherInteractor
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather.YandexCurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast.YandexForecastInteractor
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast.YandexForecastMapper
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather.YandexCurrentWeatherPresenter
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.YandexForecastPresenter

@Module
class YandexWeatherModule {

    @Provides
    @YandexWeatherScope
    fun provideYandexForecastPresenter(
        yandexForecastInteractor: YandexForecastInteractor,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): ForecastContract.Presenter =
        YandexForecastPresenter(yandexForecastInteractor, schedulerManagerModule, context)

    @Provides
    @YandexWeatherScope
    fun provideYandexForecastInteractor(
        weatherRepository: WeatherRepository,
        yandexForecastMapper: YandexForecastMapper
    ): YandexForecastInteractor = YandexForecastInteractor(weatherRepository, yandexForecastMapper)

    @Provides
    @YandexWeatherScope
    fun provideYandexForecastMapper(context: Context): YandexForecastMapper =
        YandexForecastMapper(context)

    @Provides
    @YandexWeatherScope
    fun provideYandexCurrentWeatherPresenter(
        yandexCurrentWeatherInteractor: YandexCurrentWeatherInteractor,
        schedulerManagerModule: SchedulerManagerModule,
        context: Context
    ): CurrentWeatherContract.Presenter = YandexCurrentWeatherPresenter(
        yandexCurrentWeatherInteractor, schedulerManagerModule, context
    )

    @Provides
    @YandexWeatherScope
    fun provideYandexCurrentWeatherInteractor(
        weatherRepository: WeatherRepository, yandexCurrentWeatherMapper: YandexCurrentWeatherMapper
    ): YandexCurrentWeatherInteractor =
        YandexCurrentWeatherInteractor(weatherRepository, yandexCurrentWeatherMapper)

    @Provides
    @YandexWeatherScope
    fun provideYandexCurrentWeatherMapper(context: Context): YandexCurrentWeatherMapper =
        YandexCurrentWeatherMapper(context)

}