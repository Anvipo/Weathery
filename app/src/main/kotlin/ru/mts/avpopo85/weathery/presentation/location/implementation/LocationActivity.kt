package ru.mts.avpopo85.weathery.presentation.location.implementation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.preference.PreferenceManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_location.*
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.common.LocationModule
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.AbsProgressBarActivity
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract
import ru.mts.avpopo85.weathery.presentation.map.google.MapsActivity
import ru.mts.avpopo85.weathery.presentation.utils.*
import ru.mts.avpopo85.weathery.utils.common.ExtractAddressException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.common.showAlertDialog
import javax.inject.Inject


class LocationActivity : AbsProgressBarActivity(), LocationContract.View {

    @Inject
    lateinit var presenter: LocationContract.Presenter

    override val rootLayout: CoordinatorLayout by lazy { activity_location_CL }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        toolbar.title = getString(R.string.select_current_geolocation)

        setSupportActionBar(toolbar)

        injectDependencies()

        initButtonListeners()
    }

    override fun onResume() {
        super.onResume()

        checkPlayServicesAvailable()
    }

    override fun onStart() {
        super.onStart()

        presenter.onBindView(this)
    }

    override fun onStop() {
        super.onStop()

        presenter.onUnbindView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            APPLICATION_SETTINGS_REQUEST -> onApplicationSettingsRequestCode(resultCode)
            LOCATION_BY_MAPS_REQUEST -> onLocationByMapsRequestCode(resultCode, data)
            else -> {
                val message = getString(R.string.unexpected_application_behavior)

                showIndefiniteSnackbar(message)
            }
        }
    }

    override fun showGetAddressFromCoordinatesError(error: Throwable) {
        val part1: String =
            if (error is ExtractAddressException) {
                getString(R.string.unable_to_find_the_address_of_the_specified_location)
            } else {
                showError(error)
                getString(R.string.error_has_occurred)
            }

        val part2 = getString(R.string.specify_the_location_on_the_map_again)

        val message = "$part1. $part2?"

        showAlertDialog(
            message,
            getString(R.string.yes),
            getString(R.string.no),
            { startActivityForResult<MapsActivity>(LOCATION_BY_MAPS_REQUEST) },
            title = getString(R.string.error)
        )
    }

    override fun showRationaleDialog() {
        val part1 = getString(R.string.application_needs_permissions_to_geolocation)
        val part2 = getString(R.string.provide)

        val message = "$part1. $part2?"

        showAlertDialog(
            message,
            getString(R.string.yes),
            getString(R.string.no),
            { presenter.onRationalePositiveClick() },
            { presenter.onRationaleNegativeClick() },
            getString(R.string.impossible_to_continue)
        )
    }

    override fun showGoSettingsDialog() {
        val part1 =
            getString(R.string.go_to_applications_settings_and_provide_permissions_to_geolocation)
        val part2 = getString(R.string.go_to)

        val message = "$part1. $part2?"

        showAlertDialog(
            message,
            getString(R.string.yes),
            getString(R.string.no),
            { presenter.onGoSettingsPositiveClick() },
            { presenter.onGoSettingNegativeClick() },
            getString(R.string.impossible_to_continue)
        )
    }

    override fun setResultAndFinish(address: UserAddressType) {
        val data = Intent().apply {
            putExtra(ADDRESS_TAG, address)
        }

        savePreferences(address)

        setResult(LOCATION_RESULT_OK, data)
        finish()
    }

    override fun showCityDialog(address: UserAddressType) {
        showAlertDialog(
            "${getString(R.string.is_your_current_geolocation)} - ${address.locality}?",
            getString(R.string.yes),
            getString(R.string.no),
            {
                presenter.saveAddress(address)

                setResultAndFinish(address)
            },
            title = getString(R.string.found_intended_location)
        )
    }

    override fun showLocationError() {
        showError(getString(R.string.could_not_find_your_geolocation))
    }

    override fun showLastKnownLocationError() {
        showError(getString(R.string.previous_location_unknown))
    }

    override fun enableGetLastKnownLocationButton() {
        get_last_known_location_LA_B.isEnabled = true
    }

    override fun disableGetLastKnownLocationButton() {
        get_last_known_location_LA_B.isEnabled = false
    }

    override fun changeTitle(title: String) {
        toolbar.title = title
    }

    override fun startSettingsActivityForResult() {
        // Build intent that displays the App settings screen.
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        }

        startActivityForResult(intent, APPLICATION_SETTINGS_REQUEST)
    }

    private fun initButtonListeners() {
        get_last_known_location_LA_B.setOnClickListener {
            presenter.getLastKnownGeolocation()
        }

        get_current_location_by_GPS_LA_B.setOnClickListener {
            presenter.getCurrentGeolocation()
        }

        get_current_location_by_map_LA_B.setOnClickListener {
            startActivityForResult<MapsActivity>(LOCATION_BY_MAPS_REQUEST)
        }
    }

    private fun injectDependencies() {
        App.appComponent
            .plus(LocationModule(this))
            .inject(this)

        presenter.onBindView(this)
    }

    private fun savePreferences(address: UserAddressType) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)!!

        val key = getString(R.string.pref_key_current_location)

        with(sharedPref.edit()!!) {
            putString(key, address.locality)
            apply()
        }
    }

    private fun checkPlayServicesAvailable() {
        val apiAvailability: GoogleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, 1).show()
            } else {
                val message = "${getString(R.string.google_play_services_unavailable)}. " +
                        getString(R.string.this_app_will_not_work)

                showError(message, true)
            }
        }
    }

    private fun onLocationByMapsRequestCode(resultCode: Int, data: Intent?) {
        if (resultCode == LOCATION_BY_MAPS_RESULT_OK) {
            if (data != null) {
                showLoadingProgress()

                val coordinates: LatLng? = data.getParcelableExtra(COORDINATES_TAG)

                if (coordinates != null) {
                    injectDependencies()
                    presenter.getAddressFromCoordinates(coordinates)
                } else {
                    showLocationError()
                    hideLoadingProgress()
                }
            }
        }
    }

    private fun onApplicationSettingsRequestCode(resultCode: Int) {
        if (resultCode == 0) {
            injectDependencies()
            presenter.onActivityResult()
        }
    }

}
