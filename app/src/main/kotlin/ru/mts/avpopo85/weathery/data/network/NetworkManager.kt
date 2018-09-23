package ru.mts.avpopo85.weathery.data.network

import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject

class NetworkManager
@Inject constructor(private val connectivityManager: ConnectivityManager) {

    val isConnectedToInternet: Boolean
        get() {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnected
        }

}