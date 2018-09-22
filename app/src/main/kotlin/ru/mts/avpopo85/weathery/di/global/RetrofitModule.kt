package ru.mts.avpopo85.weathery.di.global

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWCurrentWeatherParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWForecastParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWWeatherResponse
import ru.mts.avpopo85.weathery.data.network.base.CurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.base.ForecastApiService
import ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather.YWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather.YWForecastApiService
import javax.inject.Singleton

@Module
class RetrofitModule(private val baseUrl: String) {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrentWeatherApiService(retrofit: Retrofit): CurrentWeatherApiService<YWWeatherResponse, YWCurrentWeatherParameters> =
        retrofit.create(YWCurrentWeatherApiService::class.java)

    @Provides
    @Singleton
    fun provideForecastApiService(retrofit: Retrofit): ForecastApiService<YWWeatherResponse, YWForecastParameters> =
        retrofit.create(YWForecastApiService::class.java)

}