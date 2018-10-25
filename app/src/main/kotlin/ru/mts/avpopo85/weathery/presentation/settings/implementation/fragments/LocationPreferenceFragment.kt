package ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragment
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.location.implementation.LocationActivity
import ru.mts.avpopo85.weathery.presentation.utils.ADDRESS_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_REQUEST
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_RESULT_OK
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.common.showIndefiniteSnackbar
import ru.mts.avpopo85.weathery.utils.common.showLongSnackbar

class LocationPreferenceFragment : PreferenceFragment() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOCATION_REQUEST) {
            if (resultCode == LOCATION_RESULT_OK) {
                findPreference(currentLocationPrefKey)!!.apply {
                    val address: UserAddressType? = data?.getParcelableExtra(ADDRESS_TAG)

                    val locality = address?.locality ?: defaultValue

                    summary = locality
                }
            } else {
                val part1 = getString(R.string.current_location_unknown)
                val part2 = getString(R.string.you_must_find_out_it)

                view!!.showLongSnackbar("$part1. $part2")
            }
        } else {
            val message = getString(R.string.unexpected_application_behavior)

            view?.showIndefiniteSnackbar(message)
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

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_location, rootKey)

        defaultValue = getString(R.string.current_location_unknown)!!

        currentLocationPrefKey = getString(R.string.pref_key_current_location)!!

        val sharedPref = preferenceManager.sharedPreferences!!

        val currentLocation =
            sharedPref.getString(currentLocationPrefKey, defaultValue) ?: defaultValue

        findPreference(currentLocationPrefKey)!!.apply {
            summary = currentLocation

            onPreferenceChangeListener = sBindPreferenceSummaryToValueListener
        }
    }

    companion object {

        lateinit var defaultValue: String

        lateinit var currentLocationPrefKey: String

        private val sBindPreferenceSummaryToValueListener =
            Preference.OnPreferenceChangeListener { preference, value ->
                val stringValue = value.toString()

                preference.summary = stringValue

                true
            }

    }

}