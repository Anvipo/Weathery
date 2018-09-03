package ru.mts.avpopo85.weathery.utils

import java.text.DateFormat
import java.util.*

object Utils {
    fun Int.toDate(): String? {
        val date = Date(this * 1000L)
        val dateFormat = DateFormat.getDateTimeInstance()

        return dateFormat.format(date)
    }

    fun getTemperatureUnit(unitsFormat: PossibleUnits): String = when (unitsFormat) {
        PossibleUnits.METRIC -> "°C"
        PossibleUnits.STANDARD -> "K"
        PossibleUnits.IMPERIAL -> "°F"
    }

    fun getSpeedUnit(unitsFormat: PossibleUnits): String = when (unitsFormat) {
        PossibleUnits.METRIC -> "м/c"
        PossibleUnits.STANDARD -> "м/c"
        PossibleUnits.IMPERIAL -> "м/ч"
    }
}