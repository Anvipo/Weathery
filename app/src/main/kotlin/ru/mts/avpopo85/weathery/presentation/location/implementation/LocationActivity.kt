package ru.mts.avpopo85.weathery.presentation.location.implementation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.edit
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
import ru.mts.avpopo85.weathery.presentation.base.activity.withProgressBar.AbsProgressBarActivity
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract
import ru.mts.avpopo85.weathery.presentation.map.google.MapsActivity
import ru.mts.avpopo85.weathery.presentation.utils.*
import ru.mts.avpopo85.weathery.utils.common.*
import javax.inject.Inject


class LocationActivity : AbsProgressBarActivity(), LocationContract.View {

    @Inject
    lateinit var presenter: LocationContract.Presenter

    override val rootLayout: CoordinatorLayout by lazy { activity_location_CL }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        toolbar.title = getString(R.string.select_current_location)

        setSupportActionBar(toolbar)

        injectDependencies()

        initButtonListeners()
    }

    override fun onResume() {
        super.onResume()

        checkPlayServicesAvailable()
    }

    override fun onStop() {
        super.onStop()

        presenter.clearCompositeDisposable()
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            APPLICATION_SETTINGS_REQUEST ->
                onApplicationSettingsRequestForGetCurrentLocationCode(resultCode)
            LOCATION_BY_MAPS_REQUEST -> onLocationByMapsRequestCode(resultCode, data)
            else -> onUnexpectedApplicationBehavior()
        }
    }

    override fun showGetAddressFromCoordinatesError(error: Throwable) {
        val part1 = getErrorPart1(error)

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

    override fun showGetCurrentLocationRationaleDialog() {
        val part1 = getString(R.string.application_needs_permissions_to_location)
        val part2 = getString(R.string.provide)

        val message = "$part1. $part2?"

        showAlertDialog(
            message,
            getString(R.string.yes),
            getString(R.string.no),
            { presenter.onGetCurrentLocationRationalePositiveClick() },
            { presenter.onGetCurrentLocationRationaleNegativeClick() },
            getString(R.string.impossible_to_continue)
        )
    }

    override fun showGoSettingsForGetCurrentLocationDialog() {
        val part1 =
            getString(R.string.go_to_applications_settings_and_provide_permissions_to_location)
        val part2 = getString(R.string.go_to)

        val message = "$part1. $part2?"

        showAlertDialog(
            message,
            getString(R.string.yes),
            getString(R.string.no),
            { presenter.onGoSettingsForGetCurrentLocationPositiveClick() },
            { presenter.onGoSettingForGetCurrentLocationNegativeClick() },
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
            "${getString(R.string.is_your_current_location)} - ${address.locality}?",
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
        showError(getString(R.string.could_not_find_your_location))
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

    override fun getCurrentLocationByMap() {
        startActivityForResult<MapsActivity>(LOCATION_BY_MAPS_REQUEST)
    }

    private fun getErrorPart1(error: Throwable): String = when (error) {
        is ExtractAddressException -> {
            getString(R.string.unable_to_find_the_address_of_the_specified_location)
        }
        is GpsCallException.DeviceIsNotConnectedToInternetException -> {
            val defaultMessage =
                getString(R.string.internet_connection_required)

            getErrorMessage(error, defaultMessage)
        }
        else -> {
            showError(error)
            getString(R.string.error_has_occurred)
        }
    }

    private fun getErrorMessage(
        error: Throwable,
        defaultMessage: String
    ): String {
        val message = getErrorMessageOrDefault(error)

        return if (message == defaultMessage) {
            defaultMessage
        } else {
            message
        }
    }

    private fun initButtonListeners() {
        get_last_known_location_LA_B.setOnClickListener {
            presenter.onGetLastKnownLocationClick()
        }

        get_current_location_by_GPS_LA_B.setOnClickListener {
            presenter.onGetCurrentGeolocationByGPSClick()
        }

        get_current_location_by_map_LA_B.setOnClickListener {
            presenter.onGetCurrentLocationByMapClick()
        }
    }

    private fun injectDependencies() {
        App.appComponent
            .plus(LocationModule(this))
            .inject(this)

        presenter.onBindView(this)
    }

    //TODO: do it in data layer (mb)
    private fun savePreferences(address: UserAddressType) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)!!

        val key = getString(R.string.pref_key_current_location)

        sharedPref.edit(true) {
            putString(key, address.locality)
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
        when (resultCode) {
            LOCATION_BY_MAPS_RESULT_OK -> {
                if (data != null) {
                    showLoadingProgress()

                    val coordinates: LatLng? = data.getParcelableExtra(COORDINATES_TAG)

                    if (coordinates != null) {
                        presenter.onLocationByMapsRequestActivityResult(coordinates)
                    } else {
                        hideLoadingProgress()
                        showLocationError()
                    }
                }
            }
            else -> showLocationError()
        }
    }

    private fun onApplicationSettingsRequestForGetCurrentLocationCode(resultCode: Int) {
        if (resultCode == 0) {
            presenter.onApplicationSettingsRequestForGetCurrentLocationActivityResult()
        }
    }

}
