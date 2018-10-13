package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils

import android.content.Context
import io.reactivex.SingleEmitter
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasNoWeatherResponseException
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasOutdatedWeatherDataException
import java.util.*

fun Long.isFresh(cacheLifetime: Long): Boolean = Date().time - this < cacheLifetime

fun <T> Context.onDbHasNoWeatherResponse(
    isConnectedToInternet: Boolean,
    emitter: SingleEmitter<T>
) {
    val message: String =
        if (isConnectedToInternet) {
            val part1: String = getString(R.string.no_previous_weather_data)

            val part2: String = getString(R.string.get_data_from_server)

            "$part1. $part2"
        } else {
            val part1: String = getString(R.string.no_previous_weather_data)

            val part2: String = getString(R.string.internet_connection_required)

            "$part1. $part2"
        }

    val error = DBHasNoWeatherResponseException(message, isConnectedToInternet)

    emitter.onError(error)
}

fun <T> Context.onDbHasOutdatedWeatherResponse(
    emitter: SingleEmitter<T>,
    isConnectedToInternet: Boolean
) {
    val message: String =
        if (isConnectedToInternet) {
            val part1: String = getString(R.string.the_forecast_is_outdated)

            val part2: String = getString(R.string.get_data_from_server)

            "$part1. $part2"
        } else {
            val part1: String = getString(R.string.the_forecast_is_outdated)

            val part2: String = getString(R.string.internet_connection_required)

            "$part1. $part2"
        }

    val error = DBHasOutdatedWeatherDataException(message, isConnectedToInternet)

    emitter.onError(error)
}
