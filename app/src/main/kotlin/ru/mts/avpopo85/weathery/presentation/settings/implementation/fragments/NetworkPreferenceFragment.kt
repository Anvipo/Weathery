package ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragment
import ru.mts.avpopo85.weathery.R

class NetworkPreferenceFragment : PreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_network, rootKey)

        setHasOptionsMenu(true)

        weatherAPIDefaultValue = getString(R.string.not_selected)!!

        weatherAPIPrefKey = getString(R.string.pref_key_weather_API)!!

        val sharedPreferences = preferenceManager.sharedPreferences!!

        val currentLocation =
            sharedPreferences.getString(weatherAPIPrefKey, weatherAPIDefaultValue)
                ?: weatherAPIDefaultValue

        val currentGeolocationPreference = findPreference(weatherAPIPrefKey)!!

        currentGeolocationPreference.apply {
            summary = currentLocation
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
                weatherAPIPrefKey -> {
                    val currentGeolocationPreference = findPreference(key)!!

                    currentGeolocationPreference.summary =
                            sharedPreferences.getString(key, weatherAPIDefaultValue)
                }
                else -> {
                    Log.d(TAG, "unknown key")
                }
            }
        }

    companion object {

        val TAG: String = LocationPreferenceFragment::class.java.simpleName

        lateinit var weatherAPIDefaultValue: String

        lateinit var weatherAPIPrefKey: String

    }

}