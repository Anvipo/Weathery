@file:Suppress("unused")

package ru.mts.avpopo85.weathery.utils.common

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.utils.onHttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

fun Context.onUnexpectedApplicationBehavior(view: View) {
    val message = getString(R.string.unexpected_application_behavior)

    view.showIndefiniteSnackbar(message)
}

fun sendErrorLog(message: String, tag: String? = null) {
    if (tag != null) {
        Log.e(tag, message)
    } else {
        Log.e("APP", message)
    }
}

fun View.showShortSnackbar(message: CharSequence): Unit = shortSnackbar(message).show()

fun View.showLongSnackbar(message: CharSequence): Unit = longSnackbar(message).show()

fun View.showIndefiniteSnackbar(message: CharSequence): Unit = indefiniteSnackbar(message).show()

fun View.shortSnackbar(message: CharSequence) = Snackbar
    .make(this, message, Snackbar.LENGTH_SHORT)

fun View.longSnackbar(message: CharSequence) = Snackbar
    .make(this, message, Snackbar.LENGTH_LONG)

fun View.indefiniteSnackbar(message: CharSequence) = Snackbar
    .make(this, message, Snackbar.LENGTH_INDEFINITE)

fun Context.alertDialog(
    message: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onClickedPositiveButton: () -> Unit,
    onClickedNegativeButton: () -> Unit,
    title: String?
): AlertDialog =
    AlertDialog.Builder(this).apply {
        if (title != null)
            setTitle(title)

        setMessage(message)

        setNegativeButton(negativeButtonText) { _, _ -> onClickedNegativeButton() }

        setPositiveButton(positiveButtonText) { _, _ -> onClickedPositiveButton() }
    }.create()!!

fun Context.showAlertDialog(
    message: String,
    positiveButtonText: String,
    negativeButtonText: String,
    onClickedPositiveButton: () -> Unit = {},
    onClickedNegativeButton: () -> Unit = {},
    title: String? = null
) {
    alertDialog(
        message = message,
        positiveButtonText = positiveButtonText,
        negativeButtonText = negativeButtonText,
        onClickedPositiveButton = onClickedPositiveButton,
        onClickedNegativeButton = onClickedNegativeButton,
        title = title
    ).show()
}

fun Context.getFullApplicationInfo(): String = try {
    val sb = StringBuilder()

    sb.append("\n\n-----------------------------")
    sb.append("\nPlease don't remove this information")
    sb.append("\nDevice OS: Android")

    val osVersion = Build.VERSION.RELEASE

    sb.append("\nDevice OS version: $osVersion")

    val appVersion =
        packageManager
            .getPackageInfo(packageName, 0)
            .versionName

    sb.append("\nApp Version: $appVersion")

    val deviceBrand = Build.BRAND

    sb.append("\nDevice Brand: $deviceBrand")

    val deviceModel = Build.MODEL
    sb.append("\nDevice Model: $deviceModel")

    val deviceManufacturer = Build.MANUFACTURER
    sb.append("\nDevice Manufacturer: $deviceManufacturer")

    sb.toString()
} catch (e: PackageManager.NameNotFoundException) {
    ""
}

fun Context.parseError(error: Throwable): String =
    when (error) {
        is GoogleGeocodeException -> onGoogleGeocodeException()
        is TimeoutException -> onTimeoutException()
        is HttpException -> onHttpException(error)
        is UnknownHostException -> onUnknownHostException()
        is ConnectException -> onConnectException()
        is SocketTimeoutException -> onSocketTimeoutException()
        else -> getErrorMessageOrDefault(error)
    }

fun Context.getErrorMessageOrDefault(error: Throwable): String =
    error.localizedMessage ?: error.message ?: getString(R.string.unknown_error)

private fun Context.onGoogleGeocodeException(): String =
    getString(R.string.could_not_find_address_of_your_current_location)

private fun Context.onTimeoutException(): String {
    val part1 = getString(R.string.request_timeout)
    val part2 = getString(R.string.please_try_later)

    return "$part1. $part2"
}

private fun Context.onUnknownHostException(): String =
    "${getString(R.string.the_server_is_temporarily_unavailable)}. " +
            getString(R.string.please_try_later)

private fun Context.onConnectException(): String = getString(R.string.authorisation_error)

private fun Context.onSocketTimeoutException(): String =
    "${getString(R.string.response_timeout)}. " +
            getString(R.string.make_sure_your_device_has_a_network_connection)
