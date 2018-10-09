package ru.mts.avpopo85.weathery.data.db.util

import android.content.Context
import io.reactivex.SingleEmitter
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasNothing
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasOutdatedData
import java.util.*

fun Long.isFresh(cacheLifetime: Long): Boolean = Date().time - this < cacheLifetime

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

fun <T> Context.onDbHasNothing(
    isConnectedToInternet: Boolean,
    emitter: SingleEmitter<T>
) {
    val message =
        if (isConnectedToInternet) {
            val part1 = getString(R.string.db_has_nothing)

            val part2 = getString(R.string.get_data_from_server)

            "$part1. $part2"
        } else {
            val part1 = getString(R.string.db_has_nothing)

            val part2 = getString(R.string.you_have_no_internet_connection)

            "$part1. $part2"
        }

    val error = DBHasNothing(message, isConnectedToInternet)

    emitter.onError(error)
}

fun <T> Context.onDbOutdatedData(
    emitter: SingleEmitter<T>,
    isConnectedToInternet: Boolean
) {
    val message =
        if (isConnectedToInternet) {
            val part1 = getString(R.string.db_has_outdated_data)

            val part2 = getString(R.string.get_data_from_server)

            "$part1. $part2"
        } else {
            val part1 = getString(R.string.db_has_outdated_data)

            val part2 = getString(R.string.you_have_no_internet_connection)

            "$part1. $part2"
        }

    val error = DBHasOutdatedData(message, isConnectedToInternet)

    emitter.onError(error)
}