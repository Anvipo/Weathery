package ru.mts.avpopo85.weathery.presentation.welcome.implementation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_welcome.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.AbsProgressBarActivity
import ru.mts.avpopo85.weathery.presentation.location.implementation.LocationActivity
import ru.mts.avpopo85.weathery.presentation.main.MainActivity
import ru.mts.avpopo85.weathery.presentation.utils.*
import ru.mts.avpopo85.weathery.presentation.welcome.base.WelcomeContract
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

class WelcomeActivity : AbsProgressBarActivity(), WelcomeContract.View {

    override val rootLayout: View by lazy { activity_welcome_CL }

    override fun onCreate(savedInstanceState: Bundle?) {
        // убедитесь, что вызываете до super.onCreate()
        setTheme(R.style.MyAppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val sharedPreferences = getSharedPreferences(LOCALITY_PREFERENCES_NAME, Context.MODE_PRIVATE)!!

        val location = sharedPreferences.getString(LOCALITY_TAG, null)

        routeToAppropriatePage(location)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == SUCCESS_LOCATION_RESULT_CODE) {
            val address: UserAddressType? = data?.getParcelableExtra(ADDRESS_TAG)

            val locality = address?.locality

            routeToAppropriatePage(locality)
        }
    }

    private fun routeToAppropriatePage(locality: String?) {
        when (locality) {
            null -> startActivityForResult<LocationActivity>(LOCATION_REQUEST_CODE)
            else -> {
                startActivity<MainActivity>()
                finish()
            }
        }
    }

}
