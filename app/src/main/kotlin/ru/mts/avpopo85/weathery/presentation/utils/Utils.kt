package ru.mts.avpopo85.weathery.presentation.utils

import android.content.Context
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWForecast
import java.net.UnknownHostException

fun Context.makeErrorText(it: Throwable): String =
    if (it is UnknownHostException)
        "${getString(R.string.error)}.${getString(R.string.there_may_be_a_problem_with_the_Internet_connection)}."
    else
        it.message ?: getString(R.string.unknown_error)

fun Context.makeTitle(YWForecast: YWForecast): String =
    "${YWForecast.date_ts} (${YWForecast.weekSerialNumber} ${getString(R.string.week)})"