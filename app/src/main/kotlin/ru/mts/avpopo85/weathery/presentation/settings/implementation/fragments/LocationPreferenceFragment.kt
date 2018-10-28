package ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragment
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.location.implementation.LocationActivity
import ru.mts.avpopo85.weathery.presentation.utils.ADDRESS_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_REQUEST
import ru.mts.avpopo85.weathery.presentation.utils.SETTING_RESULT_OK
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.common.onUnexpectedApplicationBehavior
import ru.mts.avpopo85.weathery.utils.common.showLongSnackbar

class LocationPreferenceFragment : PreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_location, rootKey)

        setHasOptionsMenu(true)

        currentGeolocationDefaultValue = getString(R.string.unknown)!!

        currentGeolocationPrefKey = getString(R.string.pref_key_current_location)!!

        val sharedPreferences = preferenceManager.sharedPreferences!!

        val currentLocation =
            sharedPreferences.getString(currentGeolocationPrefKey, currentGeolocationDefaultValue)
                ?: currentGeolocationDefaultValue

        val currentGeolocationPreference = findPreference(currentGeolocationPrefKey)!!

        currentGeolocationPreference.summary = currentLocation
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOCATION_REQUEST) {
            if (resultCode == SETTING_RESULT_OK) {
                findPreference(currentGeolocationPrefKey)!!.apply {
                    val address: UserAddressType? = data?.getParcelableExtra(ADDRESS_TAG)

                    val locality = address?.locality ?: currentGeolocationDefaultValue

                    summary = locality
                }
            } else {
                val part1 = getString(R.string.current_location_unknown)
                val part2 = getString(R.string.you_must_find_out_it)

                view!!.showLongSnackbar("$part1. $part2")
            }
        } else {
            this.activity!!.applicationContext!!.onUnexpectedApplicationBehavior(view!!)
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean =
        when (preference.key) {
            getString(R.string.pref_key_get_location)!! -> {
                startActivityForResult(
                    Intent(activity, LocationActivity::class.java),
                    LOCATION_REQUEST
                )
                true
            }
            else -> {
                super.onPreferenceTreeClick(preference)
            }
        }

    override fun onResume() {
        super.onResume()
        preferenceScreen
            .sharedPreferences
            .registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen
            .sharedPreferences
            .unregisterOnSharedPreferenceChangeListener(listener)
    }

    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                currentGeolocationPrefKey -> {
                    val currentGeolocationPreference = findPreference(key)!!

                    currentGeolocationPreference.summary =
                            sharedPreferences.getString(key, currentGeolocationDefaultValue)
                }
                else -> {
                    Log.d(TAG, "unknown key")
                }
            }
        }

    companion object {

        val TAG: String = LocationPreferenceFragment::class.java.simpleName

        lateinit var currentGeolocationDefaultValue: String

        lateinit var currentGeolocationPrefKey: String

    }

}