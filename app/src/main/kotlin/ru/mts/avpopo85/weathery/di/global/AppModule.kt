package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.db.base.CurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ForecastDbService
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWCurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.network.base.NetworkManager
import ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather.YWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather.YWForecastApiService
import ru.mts.avpopo85.weathery.data.repository.yandexWeather.YWCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.repository.yandexWeather.YWForecastRepository
import ru.mts.avpopo85.weathery.domain.repository.CurrentWeatherRepository
import ru.mts.avpopo85.weathery.domain.repository.ForecastRepository
import ru.mts.avpopo85.weathery.utils.YWForecastResponseType
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideCurrentWeatherRepository(
        currentWeatherApiService: YWCurrentWeatherApiService,
        networkManager: NetworkManager,
        currentWeatherDbService: CurrentWeatherDbService<YWCurrentWeatherResponse>
    ): CurrentWeatherRepository<YWCurrentWeatherResponse> = YWCurrentWeatherRepository(
        currentWeatherApiService,
        networkManager,
        currentWeatherDbService
    )

    @Provides
    @Singleton
    fun provideForecastRepository(
        forecastApiService: YWForecastApiService,
        networkManager: NetworkManager,
        currentWeatherDbService: ForecastDbService<YWForecastResponseType>
    ): ForecastRepository<YWForecastResponseType> = YWForecastRepository(
        forecastApiService,
        networkManager,
        currentWeatherDbService
    )

}
