package ru.mts.avpopo85.weathery.domain.mapper.implementation.common

import android.content.Context
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.utils.MyParsingException

fun Context.getDaytimeString(daytime: String): String = when (daytime) {
    "d" -> getString(R.string.light)
    "n" -> getString(R.string.dark)
    else -> throw MyParsingException.DaytimeParsingException("${getString(R.string.unknown_parameter_value)}: $daytime")
}