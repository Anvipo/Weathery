package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMForecastApiService
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWForecastApiService
import ru.mts.avpopo85.weathery.data.repository.openWeatherMap.OWMCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.repository.openWeatherMap.OWMForecastRepository
import ru.mts.avpopo85.weathery.data.repository.yandexWeather.YWCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.repository.yandexWeather.YWForecastRepository
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create()

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideYWCurrentWeatherRepository(
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
    fun provideOWMCurrentWeatherRepository(
        currentWeatherApiService: IOWMCurrentWeatherApiService,
        networkManager: NetworkManager,
        currentWeatherDbService: ICurrentWeatherDbService<OWMCurrentWeatherResponseType>
    ): ICurrentWeatherRepository<OWMCurrentWeatherResponseType> = OWMCurrentWeatherRepository(
        currentWeatherApiService,
        networkManager,
        currentWeatherDbService
    )

    @Provides
    @Singleton
    fun provideYWForecastRepository(
        forecastApiService: IYWForecastApiService,
        networkManager: NetworkManager,
        currentWeatherDbService: IForecastDbService<YWForecastListResponseType>
    ): IForecastRepository<YWForecastListResponseType> = YWForecastRepository(
        forecastApiService,
        networkManager,
        currentWeatherDbService
    )

    @Provides
    @Singleton
    fun provideOWMForecastRepository(
        forecastApiService: IOWMForecastApiService,
        networkManager: NetworkManager,
        currentWeatherDbService: IForecastDbService<OWMForecastListResponseType>
    ): IForecastRepository<OWMForecastListResponseType> = OWMForecastRepository(
        forecastApiService,
        networkManager,
        currentWeatherDbService
    )

}
