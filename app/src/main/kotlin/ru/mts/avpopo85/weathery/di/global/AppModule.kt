package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWForecastApiService
import ru.mts.avpopo85.weathery.data.repository.yandexWeather.YWCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.repository.yandexWeather.YWForecastRepository
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.YWForecastListResponseType
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideCurrentWeatherRepository(
        currentWeatherApiService: IYWCurrentWeatherApiService,
        networkManager: NetworkManager,
        currentWeatherDbService: ICurrentWeatherDbService<YWCurrentWeatherResponseType>
    ): ICurrentWeatherRepository<YWCurrentWeatherResponseType> = YWCurrentWeatherRepository(
        currentWeatherApiService,
        networkManager,
        currentWeatherDbService
    )

    @Provides
    @Singleton
    fun provideForecastRepository(
        forecastApiService: IYWForecastApiService,
        networkManager: NetworkManager,
        currentWeatherDbService: IForecastDbService<YWForecastListResponseType>
    ): IForecastRepository<YWForecastListResponseType> = YWForecastRepository(
        forecastApiService,
        networkManager,
        currentWeatherDbService
    )

}
