package ru.mts.avpopo85.weathery.domain.mapper.implementation.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toTime(): String {
    val date = Date(this * 1000)

    val simpleDateFormat = SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    )

    return simpleDateFormat.format(date)
}

fun Long.toDate(): String {
    val date = Date(this * 1000)

    val simpleDateFormat = SimpleDateFormat(
        "dd MMM y (E)",
        Locale.getDefault()
    )

    return simpleDateFormat.format(date)
}

fun Long.toDateTime(notInMillis: Boolean = true): String {
    val coefficient = if (notInMillis) 1000 else 1

    val date = Date(this * coefficient)

    val simpleDateFormat =
        SimpleDateFormat("HH:mm dd MMM y", Locale.getDefault())

    return simpleDateFormat.format(date)
}

@Suppress("unused")
fun Long.toFullDateTime(): String {
    val date = Date(this * 1000)

    val simpleDateFormat = SimpleDateFormat("HH:mm:ss dd MMM y", Locale.getDefault())

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
