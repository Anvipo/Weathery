package ru.mts.avpopo85.weathery.presentation.settings.implementation

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceFragment
import androidx.preference.PreferenceManager
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.settings.base.SettingsContract
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.LocationPreferenceFragment
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.NetworkPreferenceFragment
import ru.mts.avpopo85.weathery.presentation.utils.LOCALITY_TAG
import ru.mts.avpopo85.weathery.presentation.utils.SETTING_RESULT_OK
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

    private fun onBackPressedInHeaders() {
        val currentLocationPrefKey = getString(R.string.pref_key_current_location)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)!!

        val currentLocation = sharedPreferences.getString(currentLocationPrefKey, null)

        val weatherAPIPrefKey = getString(R.string.pref_key_weather_API)

        val chosenWeatherAPI = sharedPreferences.getString(weatherAPIPrefKey, null)

        when {
            currentLocation == null -> {
                val part1 = getString(R.string.current_location_unknown)
                val part2 = getString(R.string.you_must_find_out_it)

                listView!!.showLongSnackbar("$part1. $part2")
            }
            chosenWeatherAPI == null -> {
                val part1 = getString(R.string.you_did_not_select_weather_API)
                val part2 = getString(R.string.you_must_select_it)

                listView!!.showLongSnackbar("$part1. $part2")
            }
            else -> {
                val intent = Intent().apply {
                    putExtra(LOCALITY_TAG, currentLocation)
                    putExtra(WEATHER_API_TAG, chosenWeatherAPI)
                }

                setResult(SETTING_RESULT_OK, intent)

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

    }
}
