package ru.mts.avpopo85.weathery.data.db.util

import io.reactivex.SingleEmitter
import ru.mts.avpopo85.weathery.BuildConfig

fun <T> onProxyDataIsNull(
    emitter: SingleEmitter<T>,
    methodName: String,
    className: String
) {
    if (BuildConfig.DEBUG) {
        val error =
            Throwable(
                "$className.$methodName " +
                        "- proxyData == null"
            )

        error.printStackTrace()

        emitter.onError(error)
    }
}

fun <T> onDataIsNull(
    emitter: SingleEmitter<T>,
    methodName: String,
    className: String
) {
    if (BuildConfig.DEBUG) {
        val error =
            Throwable(
                "$className.$methodName " +
                        "- data == null"
            )

        error.printStackTrace()

        emitter.onError(error)
    }
}