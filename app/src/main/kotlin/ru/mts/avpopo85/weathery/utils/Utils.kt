package ru.mts.avpopo85.weathery.utils

import java.text.DateFormat
import java.util.*

fun Int.toDate(): String {
    val date = Date(this * 1000L)
//    val dateTimeFormat = DateFormat.getDateTimeInstance()

    val dateFormat = DateFormat.getDateInstance()

//    val s = dateTimeFormat.format(date)!!
//    val s2 = dateFormat.format(date)!!

    return dateFormat.format(date)!!
}

object Utils {
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