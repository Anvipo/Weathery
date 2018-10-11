package ru.mts.avpopo85.weathery.data.db.util

import android.content.Context
import io.reactivex.SingleEmitter
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.*
import java.util.*

fun Long.isFresh(cacheLifetime: Long): Boolean = Date().time - this < cacheLifetime

fun <T> Context.onDbHasNoWeatherResponse(
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

    val error = DBHasNoWeatherResponseException(message, isConnectedToInternet)

    emitter.onError(error)
}

fun <T> Context.onDbOutdatedWeatherData(
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

    val error = DBHasOutdatedWeatherDataException(message, isConnectedToInternet)

    emitter.onError(error)
}