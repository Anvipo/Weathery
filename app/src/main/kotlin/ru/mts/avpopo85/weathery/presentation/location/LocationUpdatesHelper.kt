package ru.mts.avpopo85.weathery.presentation.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_location.*
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.domain.utils.toFullDateTime
import java.util.*

/**
 * Code used in requesting runtime permissions.
 */
const val FROM_LOCATION_UPDATES: Int = 35

/**
 * Constant used in the location settings dialog.
 */
const val REQUEST_CHECK_SETTINGS = 0x1

/**
 * The desired interval for location updates. Inexact. Updates may be more or less frequent.
 */
const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

/**
 * The fastest rate for active location updates. Exact. Updates will never be more frequent
 * than this value.
 */
const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

// Keys for storing activity state in the Bundle.
const val KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates"
const val KEY_LOCATION = "location"
const val KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string"

class LocationUpdatesHelper(private val activity: Activity) {

    fun initValues() {
        mRequestingLocationUpdates = false
        mLastUpdateTime = ""
    }

    fun onActivityResult(resultCode: Int) {
        when (resultCode) {
            Activity.RESULT_OK -> onResultOk()
            Activity.RESULT_CANCELED -> onResultCanceled()
        }
    }

    fun onResume() {
        if (mRequestingLocationUpdates && permissionsAreGranted()) {
            startLocationUpdates()
        } else if (!permissionsAreGranted()) {
            requestPermissions()
        }

        updateUI()
    }

    /**
     * Handles the Start Updates button and requests start of location updates. Does nothing if
     * updates have already been requested.
     */
    fun startWork() {
        if (!permissionsAreGranted()) {
            requestPermissions()
        } else if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true
            setButtonsEnabledState()
            startLocationUpdates()
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    fun stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            //updates never requested, no-op
            return
        }

        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient
            .removeLocationUpdates(mLocationCallback)
            .addOnCompleteListener(activity) {
                mRequestingLocationUpdates = false
                setButtonsEnabledState()
            }
    }

    fun onRequestPermissionsResult(grantResults: IntArray) {
        when {
            // If user interaction was interrupted, the permission request is cancelled and you
            // receive empty arrays.
            grantResults.isEmpty() -> {
                //User interaction was cancelled.
                indefiniteSnackbar(
                    activity.findViewById(android.R.id.content),
                    "grantResults.isEmpty()"
                )
                //TODO проверить эту ветку
                val c = 1
            }
            grantResults.first() == PackageManager.PERMISSION_GRANTED -> {
                //TODO проверить эту ветку
                val c = 1
                if (mRequestingLocationUpdates) {
                    indefiniteSnackbar(
                        activity.findViewById(android.R.id.content),
                        "Permission granted, updates requested, starting location updates"
                    )

                    startLocationUpdates()
                }
            }
            else -> {
                //TODO проверить эту ветку
                val c = 1
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                indefiniteSnackbar(
                    activity.findViewById(android.R.id.content),
                    "Необходимо разрешение на получения местоположения",
                    "Настройки"
                ) {
                    // Build intent that displays the App settings screen.
                    val intent = Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    activity.startActivity(intent)
                }
            }
        }
    }

    fun updateValuesFromBundle(savedInstanceState: Bundle) {
        // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
        // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
        if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
            mRequestingLocationUpdates = savedInstanceState.getBoolean(
                KEY_REQUESTING_LOCATION_UPDATES
            )
        }

        // Update the value of mCurrentLocation from the Bundle and update the UI to show the
        // correct latitude and longitude.
        if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
            // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
            // is not null.
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION)
        }

        // Update the value of mLastUpdateTime from the Bundle and update the UI.
        if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
            mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING)
        }

        updateUI()
    }

    /**
     * Updates all UI fields.
     */
    private fun updateUI() {
        setButtonsEnabledState()
        updateLocationUI()
    }

    /**
     * Creates a callback for receiving location events.
     */
    private val mLocationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                if (locationResult != null) {
                    val lastLocation = locationResult.lastLocation

                    if (locationResult.locations.size > 1) {
                        //TODO проверить эту ветку
                        indefiniteSnackbar(
                            activity.findViewById(android.R.id.content),
                            "locationResult.locations.size > 1"
                        )
                        val c = 1
                    }

                    val date = Date()

                    val nowTime = (date.time / 1000).toFullDateTime()

                    mCurrentLocation = lastLocation
                    mLastUpdateTime = nowTime

                    updateLocationUI()
                } else {
                    //TODO проверить эту ветку
                    indefiniteSnackbar(
                        activity.findViewById(android.R.id.content),
                        "locationResult == null"
                    )
                    val c = 1
                }
            }
        }
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * `ACCESS_COARSE_LOCATION` and `ACCESS_FINE_LOCATION`. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     *
     *
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     *
     *
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private val mLocationRequest: LocationRequest by lazy {
        LocationRequest().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    /**
     * Uses a [com.google.android.gms.location.LocationSettingsRequest.Builder] to build
     * a [com.google.android.gms.location.LocationSettingsRequest] that is used for checking
     * if a device has the needed location settings.
     */
    private val mLocationSettingsRequest: LocationSettingsRequest by lazy {
        LocationSettingsRequest.Builder().let {
            it.addLocationRequest(mLocationRequest)
            it.build()
        }
    }

    private val TAG = LocationActivity::class.java.simpleName

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    private var mRequestingLocationUpdates: Boolean = false

    /**
     * Represents a geographical location.
     */
    private var mCurrentLocation: Location? = null

    /**
     * Time when the location was updated represented as a String.
     */
    private var mLastUpdateTime: String? = null

    /**
     * Return the current state of the permissions needed.
     */
    private fun permissionsAreGranted(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            indefiniteSnackbar(
                activity.findViewById(android.R.id.content),
                "Необходимо разрешение на получения местоположения",
                "Окей"
            ) {
                // Request permission
                startFineLocationPermissionRequest()
            }
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startFineLocationPermissionRequest()
        }
    }

    private fun startFineLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            FROM_LAST_KNOWN_LOCATION
        )
    }

    /**
     * Disables both buttons when functionality is disabled due to insuffucient location settings.
     * Otherwise ensures that only one button is enabled at any time. The Start Updates button is
     * enabled if the user is not requesting location updates. The Stop Updates button is enabled
     * if the user is requesting location updates.
     */
    private fun setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            activity.start_updates_button.isEnabled = false
            activity.stop_updates_button.isEnabled = true
        } else {
            activity.start_updates_button.isEnabled = true
            activity.stop_updates_button.isEnabled = false
        }
    }

    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private fun updateLocationUI() {
        mCurrentLocation?.let { location ->
            val latitudeStr = "Широта: ${mCurrentLocation?.latitude}"

            activity.latitude_text.text = latitudeStr

            val longitudeStr = "Долгота: ${mCurrentLocation?.longitude}"

            activity.longitude_text.text = longitudeStr

            val lastUpdateTime = "Последнее время обновления: $mLastUpdateTime"

            activity.last_update_time_text.text = lastUpdateTime

            val otherInfo = StringBuilder().apply {
                append("accuracy: ${location.accuracy}\n")
                append("altitude: ${location.altitude}\n")
                append("bearing: ${location.bearing}\n")
                append("elapsedRealtimeNanos: ${location.elapsedRealtimeNanos}\n")
                append("isFromMockProvider: ${location.isFromMockProvider}\n")
                append("provider: ${location.provider}\n")
                append("speed: ${location.speed}\n")
                append("time: ${location.time}\n")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    append("bearingAccuracyDegrees: ${location.bearingAccuracyDegrees}\n")
                    append("speedAccuracyMetersPerSecond: ${location.speedAccuracyMetersPerSecond}\n")
                    append("verticalAccuracyMeters: ${location.verticalAccuracyMeters}\n")
                }
            }.toString()

            activity.other_info.text = otherInfo
        }
    }

    private val mSettingsClient: SettingsClient  by lazy {
        LocationServices.getSettingsClient(activity)
    }

    private val mFusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(activity)
    }

    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
     * runtime permission has been granted.
     */
    private fun startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient
            .checkLocationSettings(mLocationSettingsRequest)
            .addOnSuccessListener(activity) { locationSettingsResponse: LocationSettingsResponse? ->
                if (locationSettingsResponse != null)
                    onSuccessLocationUpdate()
                else {
                    indefiniteSnackbar(
                        activity.findViewById(android.R.id.content),
                        "locationSettingsResponse == null"
                    )
                    //TODO проверить эту ветку
                    val c = 1
                }
            }
            .addOnFailureListener(activity) { exception ->
                onFailureLocationUpdate(exception)
            }
    }

    private var resolvableApiException: ResolvableApiException? = null

    private fun onFailureLocationUpdate(exception: Exception) {
        val statusCode = (exception as ApiException).statusCode

        when (statusCode) {
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                //Location settings are not satisfied. Attempting to upgrade location settings
                try {
                    //Show the dialog by calling startResolutionForResult(),
                    //and check the result in onActivityResult().
                    resolvableApiException = exception as ResolvableApiException

                    resolvableApiException?.startResolutionForResult(
                        activity,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sie: IntentSender.SendIntentException) {
                    //PendingIntent unable to execute request.
                    //TODO проверить эту ветку
                    indefiniteSnackbar(
                        activity.findViewById(android.R.id.content),
                        "PendingIntent unable to execute request"
                    )
                    val c = 1
                }

            }
            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                //TODO проверить эту ветку
                indefiniteSnackbar(
                    activity.findViewById(android.R.id.content),
                    "Location settings are inadequate, and cannot be fixed here. Fix in Settings."
                )
                mRequestingLocationUpdates = false
            }
        }

        updateUI()
    }

    private fun onResultOk() {
        //Nothing to do. startLocationUpdates() gets called in onResume again.
        //User agreed to make required location settings changes.
    }

    private fun onResultCanceled() {
        //User chose not to make required location settings changes.
        indefiniteSnackbar(
            activity.findViewById(android.R.id.content),
            "Вы не включили геолокацию. Дальнейшая работа невозможна",
            "Включить"
        ) {
            resolvableApiException?.startResolutionForResult(
                activity,
                REQUEST_CHECK_SETTINGS
            )
        }

        mRequestingLocationUpdates = false
        updateUI()
    }

    @SuppressLint("MissingPermission")
    private fun onSuccessLocationUpdate() {
        //All location settings are satisfied.
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )

        updateUI()
    }

}