package ru.mts.avpopo85.weathery.presentation.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_location.*
import org.jetbrains.anko.design.indefiniteSnackbar
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.domain.utils.toFullDateTime

/**
 * Code used in requesting runtime permissions.
 */
const val FROM_LAST_KNOWN_LOCATION: Int = 34

class LastKnownLocationHelper(private val activity: Activity) {

    fun startWork() {
        if (!permissionsAreGranted()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    fun getLastLocation() {
        locationClient.lastLocation
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful && task.result != null) {
                    val location = task.result
                    val latitudeStr = "Широта: ${location.latitude}"

                    activity.latitude_text.text = latitudeStr

                    val longitudeStr = "Долгота: ${location.longitude}"

                    activity.longitude_text.text = longitudeStr

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
                } else {
                    activity.location_inadequate_warning.text =
                            "Предыдущее местоположение не обнаружено." +
                            "Возможно отключена геолокация.\n${task.exception}"
                }
            }
    }

    fun onRequestPermissionsResult(grantResults: IntArray) {
        when {
            // If user interaction was interrupted, the permission request is cancelled and you
            // receive empty arrays.
            grantResults.isEmpty() -> {
                indefiniteSnackbar(
                    activity.findViewById(android.R.id.content),
                    "LKLH User interaction was cancelled."
                )
                val c = 1
                //                    Log.i(TAG, "User interaction was cancelled.")
            }

            // Permission granted.
            grantResults.first() == PackageManager.PERMISSION_GRANTED -> {
                getLastLocation()
            }

            // Permission denied.

            // Notify the user via a SnackBar that they have rejected a core permission for the
            // app, which makes the Activity useless. In a real app, core permissions would
            // typically be best requested during a welcome-screen flow.

            // Additionally, it is important to remember that a permission might have been
            // rejected without asking the user for permission (device policy or "Never ask
            // again" prompts). Therefore, a user interface affordance is typically implemented
            // when permissions are denied. Otherwise, your app could appear unresponsive to
            // touches or interactions which have required permissions.
            else -> {
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

    private val locationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(activity)
    }

    private fun permissionsAreGranted(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        val shouldProvideRationale =
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

        if (shouldProvideRationale) {
            // Provide an additional rationale to the user. This would happen if the user denied the
            // request previously, but didn't check the "Don't ask again" checkbox.
            indefiniteSnackbar(
                activity.findViewById(android.R.id.content),
                "Необходимо разрешение на получения местоположения",
                "Дать разрешение"
            ) {
                startCoarseLocationPermissionRequest()
            }
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startCoarseLocationPermissionRequest()
        }
    }

    private fun startCoarseLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            FROM_LAST_KNOWN_LOCATION
        )
    }

}