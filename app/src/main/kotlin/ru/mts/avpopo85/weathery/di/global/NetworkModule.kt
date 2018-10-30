package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import dagger.Module
import dagger.Provides
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
    fun provideConnectivityManager(): ConnectivityManager = context.getSystemService()!!

}
