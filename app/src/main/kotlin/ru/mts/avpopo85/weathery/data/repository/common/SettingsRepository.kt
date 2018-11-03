package ru.mts.avpopo85.weathery.data.repository.common

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.domain.repository.ISettingsRepository
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasNoCurrentAddressException
import ru.mts.avpopo85.weathery.utils.common.SettingsInfo
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import javax.inject.Inject


class SettingsRepository
@Inject constructor(
    private val context: Context,
    private val locationDbService: ILocationDbService,
    private val networkManager: NetworkManager,
    private val sharedPreferences: SharedPreferences
) : ISettingsRepository {

    override fun checkPreferences(): Single<SettingsInfo> = if (lastAddress != null) {
        if (currentLocation == null) {
            currentLocation = lastAddress!!.locality
        }

        //todo chosenWeatherAPI
        Single.just(SettingsInfo(currentLocation!!, ""))
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

    @Suppress("unused")
    private val chosenWeatherAPI: String
        get() {
            val weatherAPIPrefKey = context.getString(R.string.pref_key_weather_API)

            val weatherAPIs = context.resources.getStringArray(R.array.weather_API)

            val weatherAPIDefaultValue = weatherAPIs[0]

            return sharedPreferences.getString(weatherAPIPrefKey, weatherAPIDefaultValue)!!
        }

}