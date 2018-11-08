package ru.mts.avpopo85.weathery.data.repository.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.domain.model.implementation.settings.LocationInfo
import ru.mts.avpopo85.weathery.domain.repository.ILocationPreferenceRepository
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasNoCurrentAddressException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import javax.inject.Inject


class LocationPreferenceRepository
@Inject constructor(
    private val context: Context,
    private val locationDbService: ILocationDbService,
    private val networkManager: NetworkManager,
    private val sharedPreferences: SharedPreferences
) : ILocationPreferenceRepository {

    override fun checkPreferences(): Single<LocationInfo> = if (lastAddress != null) {
        if (currentLocation == null) {
            currentLocation = lastAddress!!.locality
        }

        //todo chosenWeatherAPI
        Single.just(LocationInfo(currentLocation!!))
    } else {
        currentLocation = null

        val message = context.getString(R.string.find_out_your_current_location)

        Single.error(DBHasNoCurrentAddressException(message))
    }

    private var currentLocation: String? = null
        get() {
            val currentLocationPrefKey = context.getString(R.string.pref_key_current_location)

            return sharedPreferences.getString(currentLocationPrefKey, null)
        }
        set(value) {
            val currentLocationPrefKey = context.getString(R.string.pref_key_current_location)

            sharedPreferences.edit(commit = true) {
                putString(currentLocationPrefKey, value)
            }

            field = sharedPreferences.getString(currentLocationPrefKey, lastAddress?.locality)
        }

    private val lastAddress: UserAddressType?
        get() = try {
            locationDbService.getLastKnownAddress(
                networkManager.isGpsProviderEnabled, networkManager.isNetworkProviderEnabled,
                networkManager.isConnectedToInternet
            ).blockingGet()
        } catch (error: Throwable) {
            null
        }

}