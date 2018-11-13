package ru.mts.avpopo85.weathery.data.network.utils

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkManager
@Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val context: Context
) {

    val isConnectedToInternet: Boolean
        get() {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false

            return networkInfo.isConnected
        }

    val isNetworkProviderEnabled: Boolean
        get() = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    val isGpsProviderEnabled: Boolean
        get() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

}
