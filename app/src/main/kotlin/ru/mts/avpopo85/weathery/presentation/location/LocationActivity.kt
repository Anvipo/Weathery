package ru.mts.avpopo85.weathery.presentation.location

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_location.*
import org.jetbrains.anko.startActivity
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.common.LocationModule
import ru.mts.avpopo85.weathery.presentation.base.AbsBaseActivity
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract
import ru.mts.avpopo85.weathery.presentation.main.MainActivity
import javax.inject.Inject


class LocationActivity : AbsBaseActivity(), LocationContract.View {

    @Inject
    lateinit var presenter: LocationContract.Presenter

    override val progressBar: ProgressBar by lazy { location_PB }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        setSupportActionBar(toolbar)

        App.appComponent
            .plus(LocationModule(this))
            .inject(this)

        presenter.onBindView(this)

        get_last_known_location_LA_B.setOnClickListener {
            presenter.getLastKnownGeolocation()
        }

        get_current_location_by_GPS_LA_B.setOnClickListener {
            presenter.getCurrentGeolocation()
        }
    }

    override fun showLocationDialog(city: String) {
        showAlertDialog(
            "${getString(R.string.is_your_current_geolocation)} - $city?",
            getString(R.string.yes),
            getString(R.string.no),
            { startActivity<MainActivity>(); finish() },
            title = getString(R.string.found_intended_location)
        )
    }

    override fun showLocationError() {
        showLongToast(getString(R.string.could_not_find_your_geolocation))
    }

    override fun onResume() {
        super.onResume()

        checkPlayServicesAvailable()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == APPLICATION_SETTINGS_REQUEST_CODE)
            if (resultCode == 0)
                presenter.onActivityResult()
    }

    override fun showRationaleDialog() {
        val part1 = getString(
            R.string.application_needs_permissions_to_geolocation
        )

        val part2 = getString(R.string.provide)

        showAlertDialog(
            "$part1. $part2?",
            getString(R.string.yes),
            getString(R.string.no),
            { presenter.onRationalePositiveClick() },
            { presenter.onRationaleNegativeClick() },
            getString(R.string.impossible_to_continue)
        )
    }

    override fun showGoSettingsDialog() {
        val part1 = getString(
            R.string.go_to_applications_settings_and_provide_permissions_to_geolocation
        )

        val part2 = getString(R.string.go_to)

        showAlertDialog(
            "$part1. $part2?",
            getString(R.string.yes),
            getString(R.string.no),
            { presenter.onGoSettingsPositiveClick() },
            { presenter.onGoSettingNegativeClick() },
            getString(R.string.impossible_to_continue)
        )
    }

    override fun enableGetLastKnownLocationButton() {
        get_last_known_location_LA_B.isEnabled = true
    }

    override fun disableGetLastKnownLocationButton() {
        get_last_known_location_LA_B.isEnabled = false
    }

    private fun checkPlayServicesAvailable() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val status = apiAvailability.isGooglePlayServicesAvailable(this)

        if (status != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(status)) {
                apiAvailability.getErrorDialog(this, status, 1).show()
            } else {
                val message = "${getString(R.string.google_play_services_unavailable)}. " +
                        getString(R.string.this_app_will_not_work)

                showIndefiniteSnackbar(message, findViewById(android.R.id.content))
            }
        }
    }

}
