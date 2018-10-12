package ru.mts.avpopo85.weathery.data.repository.weather.utils

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.utils.UnknownLocationException

fun <T> Context.onUnknownCurrentLocation(): Single<T> {
    val message: String = getString(R.string.current_location_unknown)

    val error = UnknownLocationException(message)

    return Single.error(error)
}