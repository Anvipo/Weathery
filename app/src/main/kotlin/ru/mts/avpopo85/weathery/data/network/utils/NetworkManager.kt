package ru.mts.avpopo85.weathery.data.network.utils

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject

class NetworkManager
@Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val context: Context
) {

    val isConnectedToInternet: Boolean
        get() {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnected
        }

    val isNetworkProviderEnabled: Boolean
        get() = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    val isGpsProviderEnabled: Boolean
        get() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

}
