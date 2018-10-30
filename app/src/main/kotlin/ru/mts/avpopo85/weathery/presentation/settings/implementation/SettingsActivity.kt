package ru.mts.avpopo85.weathery.presentation.settings.implementation

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.RingtonePreference
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragment
import androidx.preference.PreferenceManager
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.settings.base.SettingsContract
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.LocationPreferenceFragment
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.NetworkPreferenceFragment
import ru.mts.avpopo85.weathery.presentation.utils.LOCALITY_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_RESULT_OK
import ru.mts.avpopo85.weathery.presentation.utils.WEATHER_API_TAG
import ru.mts.avpopo85.weathery.utils.common.showLongSnackbar

class SettingsActivity : PreferenceActivity(), SettingsContract.View {

    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.preference_headers, target)
    }

    override fun onBackPressed() {
        if (hasHeaders()) {
            onBackPressedInHeaders()
        } else {
            onBackPressedInFragment()
        }
    }

    override fun onIsMultiPane(): Boolean = isXLargeTablet(this)

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean =
        (PreferenceFragment::class.java.name == fragmentName
                || LocationPreferenceFragment::class.java.name == fragmentName
                || NetworkPreferenceFragment::class.java.name == fragmentName)

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        setupActionBar()
    }

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this)!! }

    private val currentLocation: String?
        get() {
            val currentLocationPrefKey = getString(R.string.pref_key_current_location)

            return sharedPreferences.getString(currentLocationPrefKey, null)
        }

    private val chosenWeatherAPI: String
        get() {
            val weatherAPIPrefKey = getString(R.string.pref_key_weather_API)

            val weatherAPIs = resources.getStringArray(R.array.weather_API)

            val weatherAPIDefaultValue = weatherAPIs[0]

            return sharedPreferences.getString(weatherAPIPrefKey, weatherAPIDefaultValue)!!
        }

    private fun onBackPressedInHeaders() {
        @Suppress("IntroduceWhenSubject")
        when {
            currentLocation == null -> {
                val part1 = getString(R.string.current_location_unknown)
                val part2 = getString(R.string.you_must_find_out_it)

                listView!!.showLongSnackbar("$part1. $part2")
            }
            else -> {
                val intent = Intent().apply {
                    putExtra(LOCALITY_TAG, currentLocation)
                    putExtra(WEATHER_API_TAG, chosenWeatherAPI)
                }

                setResult(LOCATION_RESULT_OK, intent)

                super.onBackPressed()
            }
        }
    }

    private fun onBackPressedInFragment() {
        super.onBackPressed()
    }

    private fun setupActionBar() {
        root.addView(toolbar, 0) // insert at top

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private val toolbar: Toolbar by lazy {
        LayoutInflater
            .from(this)
            .inflate(R.layout.settings_toolbar, root, false) as Toolbar
    }

    private val root: LinearLayout by lazy {
        findViewById<View>(android.R.id.list).parent.parent.parent as LinearLayout
    }

    companion object {

        /**
         * Helper method to determine if the device has an extra-large screen. For
         * example, 10" tablets are extra-large.
         */
        private fun isXLargeTablet(context: Context): Boolean =
            context.resources.configuration.screenLayout and
                    Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE

        private val bindPreferenceSummaryToValueListener =
            Preference.OnPreferenceChangeListener { preference, value ->
                val stringValue = value.toString()

                if (preference is ListPreference) {
                    val index = preference.findIndexOfValue(stringValue)

                    val summaryValue = if (index >= 0)
                        preference.entryValues[index]
                    else
                        null

                    preference.summary = summaryValue
                } else if (preference is RingtonePreference) {
                    if (TextUtils.isEmpty(stringValue)) {
                        preference.setSummary(R.string.pref_ringtone_silent)
                    } else {
                        val ringtone = RingtoneManager
                            .getRingtone(preference.getContext(), Uri.parse(stringValue))

                        if (ringtone == null) {
                            preference.setSummary(null)
                        } else {
                            val name = ringtone.getTitle(preference.getContext())
                            preference.setSummary(name)
                        }
                    }
                } else preference.summary = stringValue

                true
            }

        fun bindPreferenceSummaryToValue(
            preference: Preference,
            defaultValue: String
        ) {
            preference.onPreferenceChangeListener = bindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference's
            // current value.
            bindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager
                    .getDefaultSharedPreferences(preference.context)
                    .getString(preference.key, defaultValue)
            )
        }

    }
}
