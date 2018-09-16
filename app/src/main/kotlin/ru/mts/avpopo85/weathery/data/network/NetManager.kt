package ru.mts.avpopo85.weathery.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetManager(private var applicationContext: Context) {
    val isConnectedToInternet: Boolean
        get() {
            val connectivityManager: ConnectivityManager =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnected
        }
}