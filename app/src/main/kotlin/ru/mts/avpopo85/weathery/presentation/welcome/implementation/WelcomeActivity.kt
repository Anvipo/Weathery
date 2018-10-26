package ru.mts.avpopo85.weathery.presentation.welcome.implementation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_welcome.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.AbsProgressBarActivity
import ru.mts.avpopo85.weathery.presentation.main.MainActivity
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsActivity
import ru.mts.avpopo85.weathery.presentation.utils.ADDRESS_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCALITY_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_REQUEST
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_RESULT_OK
import ru.mts.avpopo85.weathery.presentation.welcome.base.WelcomeContract
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

class WelcomeActivity : AbsProgressBarActivity(), WelcomeContract.View {

    override val rootLayout: View by lazy { activity_welcome_CL }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.MyAppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)!!

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
            onUnexpectedApplicationBehavior()
        }
    }

    private fun routeToAppropriatePage(locality: String?) {
        when (locality) {
            null -> startActivityForResult<SettingsActivity>(LOCATION_REQUEST)
            else -> {
                startActivity<MainActivity>()
                finish()
            }
        }
    }

}
