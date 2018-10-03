package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMForecastApiService
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWForecastApiService
import ru.mts.avpopo85.weathery.data.repository.common.LocationRepository
import ru.mts.avpopo85.weathery.data.repository.openWeatherMap.OWMCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.repository.openWeatherMap.OWMForecastRepository
import ru.mts.avpopo85.weathery.data.repository.yandexWeather.YWCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.repository.yandexWeather.YWForecastRepository
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
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
        apiService: IYWCurrentWeatherApiService,
        networkManager: NetworkManager,
        dbService: ICurrentWeatherDbService<YWCurrentWeatherResponseType>
    ): ICurrentWeatherRepository<YWCurrentWeatherResponseType> = YWCurrentWeatherRepository(
        apiService,
        networkManager,
        dbService
    )

    @Provides
    @Singleton
    fun provideOWMCurrentWeatherRepository(
        apiService: IOWMCurrentWeatherApiService,
        networkManager: NetworkManager,
        dbService: ICurrentWeatherDbService<OWMCurrentWeatherResponseType>
    ): ICurrentWeatherRepository<OWMCurrentWeatherResponseType> = OWMCurrentWeatherRepository(
        apiService,
        networkManager,
        dbService
    )

    @Provides
    @Singleton
    fun provideYWForecastRepository(
        apiService: IYWForecastApiService,
        networkManager: NetworkManager,
        dbService: IForecastDbService<YWForecastListResponseType>
    ): IForecastRepository<YWForecastListResponseType> = YWForecastRepository(
        apiService,
        networkManager,
        dbService
    )

    @Provides
    @Singleton
    fun provideOWMForecastRepository(
        apiService: IOWMForecastApiService,
        networkManager: NetworkManager,
        dbService: IForecastDbService<OWMForecastListResponseType>
    ): IForecastRepository<OWMForecastListResponseType> = OWMForecastRepository(
        apiService,
        networkManager,
        dbService
    )

    @Provides
    @Singleton
    fun provideLocationsRepository(dbService: ILocationDbService<UserAddressType>): ILocationRepository =
        LocationRepository(context, dbService)

}
