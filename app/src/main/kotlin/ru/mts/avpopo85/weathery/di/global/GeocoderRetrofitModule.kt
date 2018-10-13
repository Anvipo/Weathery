package ru.mts.avpopo85.weathery.di.global

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.mts.avpopo85.weathery.data.network.retrofit.location.IGoogleGeocoderApiService
import javax.inject.Named
import javax.inject.Singleton

@Module
class GeocoderRetrofitModule(private val baseUrl: String) {

    @Provides
    @Singleton
    @Named("GeocoderRetrofit")
    fun provideGeocoderRetrofit(gson: Gson): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun provideIYWCurrentWeatherApiService(@Named("GeocoderRetrofit") retrofit: Retrofit):
            IGoogleGeocoderApiService = retrofit.create(IGoogleGeocoderApiService::class.java)


}