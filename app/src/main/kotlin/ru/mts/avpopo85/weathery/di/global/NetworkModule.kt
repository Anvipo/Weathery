package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import javax.inject.Singleton

@Module
class NetworkModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideNetworkManager(connectivityManager: ConnectivityManager): NetworkManager =
        NetworkManager(connectivityManager, context)

    @Provides
    @Singleton
    fun provideConnectivityManager(): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}