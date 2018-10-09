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
import ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.OWMCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.repository.weather.openWeatherMap.OWMForecastRepository
import ru.mts.avpopo85.weathery.data.repository.weather.yandexWeather.YWCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.repository.weather.yandexWeather.YWForecastRepository
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType
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
        currentWeatherDbService: ICurrentWeatherDbService<YWCurrentWeatherResponseType>,
        locationDbService: ILocationDbService<UserAddressType>
    ): ICurrentWeatherRepository<YWCurrentWeatherResponseType> = YWCurrentWeatherRepository(
        apiService,
        networkManager,
        currentWeatherDbService,
        locationDbService,
        context
    )

    @Provides
    @Singleton
    fun provideOWMCurrentWeatherRepository(
        apiService: IOWMCurrentWeatherApiService,
        networkManager: NetworkManager,
        currentWeatherDbService: ICurrentWeatherDbService<OWMCurrentWeatherResponseType>,
        locationDbService: ILocationDbService<UserAddressType>
    ): ICurrentWeatherRepository<OWMCurrentWeatherResponseType> = OWMCurrentWeatherRepository(
        apiService,
        networkManager,
        currentWeatherDbService,
        locationDbService,
        context
    )

    @Provides
    @Singleton
    fun provideYWForecastRepository(
        apiService: IYWForecastApiService,
        networkManager: NetworkManager,
        forecastDbService: IForecastDbService<YWForecastResponseType>,
        locationDbService: ILocationDbService<UserAddressType>
    ): IForecastRepository<YWForecastListResponseType> = YWForecastRepository(
        apiService,
        networkManager,
        forecastDbService,
        locationDbService,
        context
    )

    @Provides
    @Singleton
    fun provideOWMForecastRepository(
        apiService: IOWMForecastApiService,
        networkManager: NetworkManager,
        forecastDbService: IForecastDbService<OWMForecastResponseType>,
        locationDbService: ILocationDbService<UserAddressType>
    ): IForecastRepository<OWMForecastListResponseType> = OWMForecastRepository(
        apiService,
        networkManager,
        forecastDbService,
        locationDbService,
        context
    )

    @Provides
    @Singleton
    fun provideLocationsRepository(
        dbService: ILocationDbService<UserAddressType>,
        networkManager: NetworkManager
    ): ILocationRepository =
        LocationRepository(context, dbService, networkManager)

}
