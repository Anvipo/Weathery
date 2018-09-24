package ru.mts.avpopo85.weathery.domain.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Int.toTime(): String {
    val date = Date(this * 1000L)

    val simpleDateFormat = SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    )

    return simpleDateFormat.format(date)
}

fun Int.toDate(): String {
    val date = Date(this * 1000L)

    val simpleDateFormat = SimpleDateFormat(
        "dd MMM YYYY (E)",
        Locale.getDefault()
    )

    return simpleDateFormat.format(date)
}

@Suppress("unused")
fun Int.toDateTime(): String {
    val date = Date(this * 1000L)

    val simpleDateFormat = SimpleDateFormat(
        "HH:mm dd MMM YYYY",
        Locale.getDefault()
    )

    return simpleDateFormat.format(date)
}

fun Double.roundIfNeeded(): String {
    val res = this.toInt()
    val fraction = res - this
    val needed = fraction == 0.0

    return if (needed)
        "$res"
    else
        "$this"
}