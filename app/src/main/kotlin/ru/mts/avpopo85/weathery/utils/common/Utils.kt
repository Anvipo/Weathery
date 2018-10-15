package ru.mts.avpopo85.weathery.utils.common

import android.util.Log

fun sendErrorLog(message: String, tag: String? = null) {
    if (tag != null) {
        Log.e(tag, message)
    } else {
        Log.e("APP", message)
    }
}
