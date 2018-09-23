package ru.mts.avpopo85.weathery.di.global

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWForecastApiService
import javax.inject.Singleton

@Module
class RetrofitModule(private val baseUrl: String) {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideYWCurrentWeatherApiService(retrofit: Retrofit): IYWCurrentWeatherApiService =
        retrofit.create(IYWCurrentWeatherApiService::class.java)

    @Provides
    @Singleton
    fun provideYWForecastApiService(retrofit: Retrofit): IYWForecastApiService =
        retrofit.create(IYWForecastApiService::class.java)

}