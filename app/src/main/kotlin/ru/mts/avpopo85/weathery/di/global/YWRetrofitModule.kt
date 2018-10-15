package ru.mts.avpopo85.weathery.di.global

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWForecastApiService
import javax.inject.Named
import javax.inject.Singleton

@Module
class YWRetrofitModule(private val baseUrl: String) {

    @Provides
    @Singleton
    @Named("YWRetrofit")
    fun provideYWRetrofit(gson: Gson): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideIYWCurrentWeatherApiService(@Named("YWRetrofit") retrofit: Retrofit): IYWCurrentWeatherApiService =
        retrofit.create(IYWCurrentWeatherApiService::class.java)

    @Provides
    @Singleton
    fun provideIYWForecastApiService(@Named("YWRetrofit") retrofit: Retrofit): IYWForecastApiService =
        retrofit.create(IYWForecastApiService::class.java)

}
