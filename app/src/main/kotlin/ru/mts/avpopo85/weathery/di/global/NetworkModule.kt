package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.network.retrofit.location.IGoogleGeocoderApiService
import ru.mts.avpopo85.weathery.data.network.utils.GoogleGeocoder
import ru.mts.avpopo85.weathery.data.network.utils.IGeocoder
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import javax.inject.Singleton

@Module
class NetworkModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideNetworkManager(connectivityManager: ConnectivityManager): NetworkManager =
        NetworkManager(connectivityManager, context)

    @Provides
    @Singleton
    fun provideGoogleGeocoder(geocoderApiService: IGoogleGeocoderApiService): IGeocoder =
        GoogleGeocoder(geocoderApiService, context)

    @Provides
    @Singleton
    fun provideConnectivityManager(): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}
