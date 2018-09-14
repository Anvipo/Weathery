package ru.mts.avpopo85.weathery.utils

import android.content.Context
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.Forecast
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun Int.toDate(): String {
    val date = Date(this * 1000L)

    val simpleDateFormat = SimpleDateFormat("dd MMM YYYY (E)", Locale.getDefault())

    return simpleDateFormat.format(date)
}

fun Context.makeTitle(forecast: Forecast): String =
    "${forecast.date_ts} (${forecast.weekSerialNumber} ${getString(R.string.week)})"

fun Double.roundIfNeeded(): String {
    val res = this.toInt()
    val fraction = res - this
    val needed = fraction == 0.0

    return if (needed)
        "$res"
    else
        "$this"
}

fun Context.makeErrorText(it: Throwable): String = if (it is UnknownHostException)
    "${getString(R.string.error)}.${getString(R.string.there_may_be_a_problem_with_the_Internet_connection)}."
else
    it.message ?: getString(R.string.unknown_error)
