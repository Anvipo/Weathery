package ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.utils

import android.content.Context
import io.reactivex.SingleEmitter
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasNoWeatherResponseException
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasOutdatedWeatherDataException
import java.util.Date

fun Long.isFresh(cacheLifetimeInMs: Long): Boolean {
    val nowTime = Date().time

    return if (this > nowTime) {
        true
    } else {
        nowTime - this < cacheLifetimeInMs
    }
}

fun <T> Context.onDbHasNoWeatherResponse(
    isConnectedToInternet: Boolean,
    emitter: SingleEmitter<T>
) {
    val message: String = if (isConnectedToInternet) {
        getString(R.string.get_data_from_server)
    } else {
        getString(R.string.internet_connection_required)
    }

    val error = DBHasNoWeatherResponseException(message, isConnectedToInternet)

    emitter.onError(error)
}

fun <T> Context.onDbHasOutdatedWeatherResponse(
    emitter: SingleEmitter<T>,
    isConnectedToInternet: Boolean,
    isLocationChanged: Boolean = false
) {
    @Suppress("LocalVariableName")
    val part1 =
        if (isLocationChanged) {
            getString(R.string.your_location_has_changed)
        } else {
            getString(R.string.the_forecast_is_outdated)
        }

    val part2 = if (isConnectedToInternet) {
        getString(R.string.get_data_from_server)
    } else {
        getString(R.string.internet_connection_required)
    }

    val message = "$part1. $part2"

    val error = DBHasOutdatedWeatherDataException(message, isConnectedToInternet)

    emitter.onError(error)
}
