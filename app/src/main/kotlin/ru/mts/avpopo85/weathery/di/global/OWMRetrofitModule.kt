package ru.mts.avpopo85.weathery.di.global

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMCurrentWeatherApiService
import javax.inject.Named
import javax.inject.Singleton

@Module
class OWMRetrofitModule(private val baseUrl: String) {

    @Provides
    @Singleton
    @Named("OWMRetrofit")
    fun provideOWMRetrofit(gson: Gson): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideIOWMCurrentWeatherApiService(@Named("OWMRetrofit") retrofit: Retrofit): IOWMCurrentWeatherApiService =
        retrofit.create(IOWMCurrentWeatherApiService::class.java)

}