package ru.mts.avpopo85.weathery.data.repository.weather.utils

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.utils.common.UnknownErrorException

fun <T> Context.onUnknownCurrentLocation(): Single<T> {
    val message: String = getString(R.string.current_location_unknown)

    val error = UnknownLocationException(message)

    return Single.error(error)
}

fun <T> Context.onUnknownError(throwable: Throwable): Single<T> {
    val message: String = getString(R.string.unknown_error)

    val error = UnknownErrorException(message, throwable)

    return Single.error(error)
}
