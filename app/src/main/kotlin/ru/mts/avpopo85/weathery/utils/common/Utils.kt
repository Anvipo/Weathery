package ru.mts.avpopo85.weathery.utils.common

import android.util.Log
import io.reactivex.SingleEmitter

fun <T> onParameterIsNull(
    emitter: SingleEmitter<T>,
    className: String,
    methodName: String,
    parameterName: String
) {
    val message = "$className.$methodName - $parameterName == null"

    val error = Throwable(message)

    sendErrorLog(message)

    error.printStackTrace()

    emitter.onError(error)
}

fun sendErrorLog(message: String, tag: String? = null) {
    if (tag != null) {
        Log.e(tag, message)
    } else {
        Log.e("APP", message)
    }
}