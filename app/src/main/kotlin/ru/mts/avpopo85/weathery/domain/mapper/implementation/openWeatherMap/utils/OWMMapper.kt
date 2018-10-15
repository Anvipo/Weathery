package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap.utils

import android.content.Context
import ru.mts.avpopo85.weathery.R

fun Context.getWindDirectionString(directionInDegrees: Double): String {
    var temp = directionInDegrees

    val direction =
        if (temp > 360.0) {
            while (temp > 360.0)
                temp -= 360.0

            temp
        } else
            temp

    return when (direction) {
        in 22.0..67.0 -> getString(R.string.northeast)
        in 67.0..112.0 -> getString(R.string.east)
        in 112.0..157.0 -> getString(R.string.southeast)
        in 157.0..202.0 -> getString(R.string.south)
        in 202.0..247.0 -> getString(R.string.southwest)
        in 247.0..292.0 -> getString(R.string.west)
        in 292.0..337.0 -> getString(R.string.northwest)
        in 337.0..360.0 -> getString(R.string.north)
        in 0.0..22.0 -> getString(R.string.north)
        else -> ""
    }
}
