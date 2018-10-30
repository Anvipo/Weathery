package ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments

import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.PreferenceFragment
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsActivity

class NetworkPreferenceFragment : PreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_network, rootKey)

        setHasOptionsMenu(true)

        val weatherAPIs = resources.getStringArray(R.array.weather_API)

        weatherAPIDefaultValue = weatherAPIs[0]

        weatherAPIPrefKey = getString(R.string.pref_key_weather_API)!!

        val sharedPreferences = preferenceManager.sharedPreferences!!

        sharedPreferences.edit {
            putString(weatherAPIPrefKey, weatherAPIDefaultValue)
        }

        val currentWeatherAPI =
            sharedPreferences.getString(weatherAPIPrefKey, weatherAPIDefaultValue)!!

        val weatherAPIPreference = findPreference(weatherAPIPrefKey)!!

        SettingsActivity.bindPreferenceSummaryToValue(weatherAPIPreference, currentWeatherAPI)
    }

    companion object {

        lateinit var weatherAPIDefaultValue: String

        lateinit var weatherAPIPrefKey: String

    }

}