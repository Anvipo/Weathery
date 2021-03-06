package ru.mts.avpopo85.weathery.presentation.welcome.implementation

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.activity.withProgressBar.AbsProgressBarActivity
import ru.mts.avpopo85.weathery.presentation.base.utils.finishThisActivity
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsActivity
import ru.mts.avpopo85.weathery.presentation.utils.ADDRESS_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCALITY_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_REQUEST
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_RESULT_OK
import ru.mts.avpopo85.weathery.presentation.weather.tab.TabbedWeatherActivity
import ru.mts.avpopo85.weathery.presentation.welcome.base.WelcomeContract
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.common.startActivity
import ru.mts.avpopo85.weathery.utils.common.startActivityForResult

class WelcomeActivity : AbsProgressBarActivity(), WelcomeContract.View {

    override val rootLayout: View by lazy { TODO() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        routeToAppropriatePage()
    }

    private fun routeToAppropriatePage() {
        val currentLocationPrefKey = getString(R.string.pref_key_current_location)

        val currentLocation = sharedPreferences.getString(currentLocationPrefKey, null)

        routeToAppropriatePage(currentLocation)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == LOCATION_RESULT_OK) {
            val address: UserAddressType? = data?.getParcelableExtra(ADDRESS_TAG)
            val localityExtra: String? = data?.getStringExtra(LOCALITY_TAG)

            val locality =
                when {
                    address != null -> address.locality
                    localityExtra != null -> localityExtra
                    else -> {
                        Toast.makeText(
                            baseContext,
                            getString(R.string.current_location_unknown),
                            Toast.LENGTH_LONG
                        ).show()

                        null
                    }
                }

            routeToAppropriatePage(locality)
        } else {
            routeToAppropriatePage()
        }
    }

    override fun changeTitle(title: String) = Unit

    private val sharedPreferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)!!
    }

    private fun routeToAppropriatePage(locality: String?) {
        when (locality) {
            null -> startActivityForResult<SettingsActivity>(LOCATION_REQUEST)
            else -> {
                startActivity<TabbedWeatherActivity>()
                finishThisActivity()
            }
        }
    }

}
