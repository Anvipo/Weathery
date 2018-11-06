@file:Suppress("unused")

package ru.mts.avpopo85.weathery.domain.mapper.implementation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toTime(inSeconds: Boolean = true): String {
    val coefficient = if (inSeconds) 1000 else 1

    val date = Date(this * coefficient)

    val simpleDateFormat = SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    )

    return simpleDateFormat.format(date)
}

fun Long.toDate(inSeconds: Boolean = true): String {
    val coefficient = if (inSeconds) 1000 else 1

    val date = Date(this * coefficient)

    val simpleDateFormat = SimpleDateFormat(
        "dd MMM y (E)",
        Locale.getDefault()
    )

    return simpleDateFormat.format(date)
}

fun Long.toDateTime(inSeconds: Boolean = true): String {
    val coefficient = if (inSeconds) 1000 else 1

    val date = Date(this * coefficient)

    val simpleDateFormat =
        SimpleDateFormat("HH:mm dd MMM y", Locale.getDefault())

    return simpleDateFormat.format(date)
}

@Suppress("unused")
fun Long.toFullDateTime(inSeconds: Boolean = true): String {
    val coefficient = if (inSeconds) 1000 else 1

    val date = Date(this * coefficient)

    val simpleDateFormat = SimpleDateFormat("HH:mm dd MMMM y", Locale.getDefault())

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
