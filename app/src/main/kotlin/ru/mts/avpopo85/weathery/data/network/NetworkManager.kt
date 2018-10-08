package ru.mts.avpopo85.weathery.data.network

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

    private val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    val isConnectedToInternet: Boolean
        get() {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

            return networkInfo != null && networkInfo.isConnected
        }

    val isNetworkEnabled: Boolean
        get() = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    val isGpsEnabled: Boolean
        get() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

}