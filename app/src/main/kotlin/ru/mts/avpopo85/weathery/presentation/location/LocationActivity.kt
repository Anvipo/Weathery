package ru.mts.avpopo85.weathery.presentation.location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_location.*
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.R


@Suppress("PrivatePropertyName")
class LocationActivity : AppCompatActivity() {

    private val lastKnownLocationHelper: LastKnownLocationHelper by lazy {
        LastKnownLocationHelper(this)
    }
    private val locationUpdatesHelper: LocationUpdatesHelper by lazy {
        LocationUpdatesHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        setSupportActionBar(toolbar)

        locationUpdatesHelper.initValues()

        if (savedInstanceState != null)
            locationUpdatesHelper.updateValuesFromBundle(savedInstanceState)

        last_location_LA_B.setOnClickListener {
            lastKnownLocationHelper.startWork()
        }

        start_updates_button.setOnClickListener {
            locationUpdatesHelper.startWork()
        }

        stop_updates_button.setOnClickListener {
            // Remove location updates to save battery.
            locationUpdatesHelper.stopLocationUpdates()
        }
    }

    override fun onResume() {
        super.onResume()

        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val result = googleApiAvailability.isGooglePlayServicesAvailable(this)

        if (result != ConnectionResult.SUCCESS && result != ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED) {
            longToast(
                "Кажется программа запущена на эмуляторе," +
                        " и скорее всего геолокация (и/или что-нибудь ещё) не будет работать на нём"
            )
        }

        locationUpdatesHelper.onResume()
    }

    override fun onPause() {
        super.onPause()

        // Remove location updates to save battery.
        locationUpdatesHelper.stopLocationUpdates()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            REQUEST_CHECK_SETTINGS -> locationUpdatesHelper.onActivityResult(resultCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            FROM_LOCATION_UPDATES -> locationUpdatesHelper.onRequestPermissionsResult(grantResults)
            FROM_LAST_KNOWN_LOCATION -> lastKnownLocationHelper.onRequestPermissionsResult(grantResults)
        }
    }

}
