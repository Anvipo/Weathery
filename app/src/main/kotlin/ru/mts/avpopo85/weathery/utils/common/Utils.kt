@file:Suppress("unused")

package ru.mts.avpopo85.weathery.utils.common

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import ru.mts.avpopo85.weathery.R

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
    AlertDialog.Builder(this, R.style.MyDialog).apply {
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
