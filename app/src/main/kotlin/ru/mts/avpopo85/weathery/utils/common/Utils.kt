@file:Suppress("unused", "UNUSED_VARIABLE")

package ru.mts.avpopo85.weathery.utils.common

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.utils.startActivity
import ru.mts.avpopo85.weathery.presentation.base.utils.startActivityForResult
import ru.mts.avpopo85.weathery.presentation.utils.onHttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

@IntDef(value = [Toast.LENGTH_SHORT, Toast.LENGTH_LONG])
@Retention(AnnotationRetention.SOURCE)
annotation class ToastDuration

@IntDef(value = [Snackbar.LENGTH_SHORT, Snackbar.LENGTH_INDEFINITE, Snackbar.LENGTH_LONG])
@Retention(AnnotationRetention.SOURCE)
annotation class SnackbarDuration

inline fun <reified T : Any> T.error(msg: Any?, tag: String? = null, tr: Throwable? = null) {
    val tagStr = tag ?: this::class.java.simpleName

    val message = msg?.toString()

    Log.e(tagStr, message, tr)
}

inline fun <reified T : Any> T.debug(msg: Any?, tag: String? = null, tr: Throwable? = null) {
    val tagStr = tag ?: this::class.java.simpleName

    val message = msg?.toString()

    Log.d(tagStr, message, tr)
}

inline fun <reified T : Context> Context.startActivity(vararg params: Pair<String, Any?>) {
    startActivity(this, T::class.java, params)
}

inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) {
    startActivityForResult(
        this,
        T::class.java,
        requestCode,
        params
    )
}

//todo
fun convert(value: Double, from: TemperatureUnit, target: TemperatureUnit): Double =
    if (from == TemperatureUnit.CELSIUS && target == TemperatureUnit.KELVIN) {
        value + 273.15
    } else if (from == TemperatureUnit.KELVIN && target == TemperatureUnit.CELSIUS) {
        value - 273.15
    } else if (from == TemperatureUnit.FAHRENHEIT && target == TemperatureUnit.CELSIUS) {
        (value - 32) / 1.8
    } else if (from == TemperatureUnit.CELSIUS && target == TemperatureUnit.FAHRENHEIT) {
        (value * 1.8) + 32
    } else if (from == TemperatureUnit.FAHRENHEIT && target == TemperatureUnit.KELVIN) {
        (value + 459.67) * (5 / 9)
    } else if (from == TemperatureUnit.KELVIN && target == TemperatureUnit.FAHRENHEIT) {
        (value * (5 / 9)) - 459.67
    } else {
        value
    }

enum class TemperatureUnit {
    CELSIUS,
    KELVIN,
    FAHRENHEIT
}

fun Context.onUnexpectedApplicationBehavior(view: View) {
    val message = getString(R.string.unexpected_application_behavior)

    view.showSnackbar(message, Snackbar.LENGTH_INDEFINITE)
}

fun Context.toast(message: CharSequence, @ToastDuration duration: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(this, message, duration)

fun Context.showToast(message: CharSequence, @ToastDuration duration: Int = Toast.LENGTH_LONG) {
    toast(message, duration).show()
}

fun View.snackbar(message: CharSequence, @SnackbarDuration duration: Int = Snackbar.LENGTH_LONG) =
    Snackbar.make(this, message, duration)

fun View.showSnackbar(message: CharSequence, @SnackbarDuration duration: Int = Snackbar.LENGTH_LONG) {
    snackbar(message, duration).show()
}

fun View.showShortSnackbar(message: CharSequence) {
    showSnackbar(message, Snackbar.LENGTH_SHORT)
}

fun View.showLongSnackbar(message: CharSequence) {
    showSnackbar(message, Snackbar.LENGTH_LONG)
}

fun View.showIndefiniteSnackbar(message: CharSequence) {
    showSnackbar(message, Snackbar.LENGTH_INDEFINITE)
}

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

//todo
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
        is TimeoutException -> onTimeoutException(error)
        is HttpException -> onHttpException(error)
        is UnknownHostException -> onUnknownHostException(error)
        is ConnectException -> onConnectException(error)
        is SocketTimeoutException -> onSocketTimeoutException(error)
        is IOException -> onIOException(error)
        else -> getErrorMessageOrDefault(error)
    }

private fun Context.onIOException(error: IOException): String {
    val msg = getErrorMessageOrDefault(error)

    val part1 = getString(R.string.internet_connection_error_occurs)
    val part2 = getString(R.string.please_try_later)

    return "$part1. $part2"
}

fun Context.getErrorMessageOrDefault(error: Throwable): String =
    error.localizedMessage ?: error.message ?: getString(R.string.unknown_error)

private fun Context.onTimeoutException(error: TimeoutException): String {
    val msg = getErrorMessageOrDefault(error)

    val part1 = getString(R.string.request_timeout)
    val part2 = getString(R.string.please_try_later)

    return "$part1. $part2"
}

private fun Context.onUnknownHostException(error: UnknownHostException): String {
    val msg = getErrorMessageOrDefault(error)

    return "${getString(R.string.error_occurs)}. ${getString(R.string.please_try_later)}"
}

private fun Context.onConnectException(error: ConnectException): String {
    val msg = getErrorMessageOrDefault(error)

    return getString(R.string.authorisation_error)
}

private fun Context.onSocketTimeoutException(error: SocketTimeoutException): String {
    val msg = getErrorMessageOrDefault(error)

    return "${getString(R.string.error_occurs)}. ${getString(R.string.check_network_connection)}"
}
