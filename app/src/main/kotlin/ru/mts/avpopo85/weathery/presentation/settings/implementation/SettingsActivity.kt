package ru.mts.avpopo85.weathery.presentation.settings.implementation

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.preference.PreferenceActivity
import androidx.preference.PreferenceFragment
import androidx.preference.PreferenceManager
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.settings.base.SettingsContract
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.LocationPreferenceFragment
import ru.mts.avpopo85.weathery.presentation.utils.LOCALITY_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_RESULT_OK
import ru.mts.avpopo85.weathery.utils.common.showLongSnackbar

class SettingsActivity : PreferenceActivity(), SettingsContract.View {

    override fun onBackPressed() {
        val currentLocationPrefKey = getString(R.string.pref_key_current_location)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)!!

        val currentLocation = sharedPreferences.getString(currentLocationPrefKey, null)

        if (currentLocation == null) {
            val part1 = getString(R.string.current_location_unknown)
            val part2 = getString(R.string.you_must_find_out_it)

            listView!!.showLongSnackbar("$part1. $part2")
        } else {
            val intent = Intent().apply {
                putExtra(LOCALITY_TAG, currentLocation)
            }

            setResult(LOCATION_RESULT_OK, intent)

            super.onBackPressed()
        }
    }

    override fun onIsMultiPane(): Boolean = isXLargeTablet(this)

    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.pref_headers, target)
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean =
        (PreferenceFragment::class.java.name == fragmentName
                || LocationPreferenceFragment::class.java.name == fragmentName)

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
