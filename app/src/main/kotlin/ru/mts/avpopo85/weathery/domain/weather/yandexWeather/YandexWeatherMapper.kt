package ru.mts.avpopo85.weathery.domain.weather.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.utils.MyParsingException.*
import ru.mts.avpopo85.weathery.utils.roundIfNeeded

fun Context.getCloudinessString(cloudiness: Double): String = when (cloudiness) {
    0.0 -> getString(R.string.clear)
    0.25 -> getString(R.string.partly_cloudy)
    0.5, 0.75 -> getString(R.string.cloudy)
    1.0 -> getString(R.string.overcast)
    else -> throw CloudinessParsingException("${getString(R.string.unknown_parameter_value)}: $cloudiness")
}

fun Context.getWeatherDescriptionString(season: String): String = when (season) {
    "clear" -> getString(R.string.clear)
    "partly-cloudy" -> getString(R.string.partly_cloudy)
    "cloudy" -> getString(R.string.cloudy)
    "overcast" -> getString(R.string.overcast)
    "partly-cloudy-and-light-rain", "cloudy-and-light-rain", "overcast-and-light-rain" ->
        getString(R.string.light_rain)
    "partly-cloudy-and-rain", "cloudy-and-rain" -> getString(R.string.rain)
    "overcast-and-rain" -> getString(R.string.heavy_rain)
    "overcast-thunderstorms-with-rain" -> getString(R.string.heavy_rain_thunderstorm)
    "overcast-and-wet-snow" -> getString(R.string.sleet)
    "partly-cloudy-and-light-snow", "cloudy-and-light-snow", "overcast-and-light-snow" ->
        getString(R.string.light_snow)
    "partly-cloudy-and-snow", "cloudy-and-snow" -> getString(R.string.snow)
    "overcast-and-snow" -> getString(R.string.snowfall)
    else -> throw WeatherDescriptionParsingException("${getString(R.string.unknown_parameter_value)}: $season")
}

fun Context.getPrecipitationStrengthString(precipitationStrength: Double): String =
    when (precipitationStrength) {
        0.0 -> getString(R.string.no_precipitation)
        0.25 -> getString(R.string.light_rain_or_snow)
        0.5 -> getString(R.string.rain_or_snow)
        0.75 -> getString(R.string.heavy_rain_or_snowfall)
        1.0 -> getString(R.string.heavy_downpour_or_snowstorm)
        else -> throw PrecipitationStrengthParsingException("${getString(R.string.unknown_parameter_value)}: $precipitationStrength")
    }

fun Context.getPrecipitationTypeString(precipitationStrengthType: Int): String =
    when (precipitationStrengthType) {
        0 -> getString(R.string.no_precipitation)
        1 -> getString(R.string.rain)
        2 -> getString(R.string.sleet)
        3 -> getString(R.string.snow)
        else -> throw PrecipitationTypeParsingException("${getString(R.string.unknown_parameter_value)}: $precipitationStrengthType")
    }

fun Context.getWindDirectionString(windDirection: String): String = when (windDirection) {
    "nw" -> getString(R.string.northwest)
    "n" -> getString(R.string.north)
    "ne" -> getString(R.string.northeast)
    "e" -> getString(R.string.east)
    "se" -> getString(R.string.southeast)
    "s" -> getString(R.string.south)
    "sw" -> getString(R.string.southwest)
    "w" -> getString(R.string.west)
    "c" -> getString(R.string.calm)
    else -> throw WindDirectionParsingException("${getString(R.string.unknown_parameter_value)}: $windDirection")
}

fun Context.getDaytimeString(daytime: String): String = when (daytime) {
    "d" -> getString(R.string.light)
    "n" -> getString(R.string.dark)
    else -> throw DaytimeParsingException("${getString(R.string.unknown_parameter_value)}: $daytime")
}

fun Context.getSeasonString(season: String): String = when (season) {
    "summer" -> getString(R.string.summer)
    "autumn" -> getString(R.string.autumn)
    "winter" -> getString(R.string.winter)
    "spring" -> getString(R.string.spring)
    else -> throw SeasonParsingException("${getString(R.string.unknown_parameter_value)}: $season")
}

fun Context.getMoonCodeString(moonCode: Int): String = when (moonCode) {
    8 -> getString(R.string.new_moon)
    12 -> getString(R.string.first_quarter)
    in 9..11, in 13..15 -> getString(R.string.growing_moon)
    0 -> getString(R.string.full_moon)
    in 1..3, in 5..7 -> getString(R.string.decreasing_moon)
    4 -> getString(R.string.last_quarter)
    else -> throw MoonCodeParsingException("${getString(R.string.unknown_parameter_value)}: $moonCode")
}

fun Context.getMoonTextString(moonText: String): String = when (moonText) {
    "new-moon" -> getString(R.string.new_moon)
    "first-quarter" -> getString(R.string.first_quarter)
    "growing-moon" -> getString(R.string.growing_moon)
    "full-moon" -> getString(R.string.full_moon)
    "decreasing-moon" -> getString(R.string.decreasing_moon)
    "last-quarter" -> getString(R.string.last_quarter)
    else -> throw MoonTextParsingException("${getString(R.string.unknown_parameter_value)}: $moonText")
}

fun Context.getPolarString(polar: Boolean): String =
    if (polar) getString(R.string.yes) else getString(R.string.no)

object YandexWeatherMapper {
    fun getWaterTemperatureString(waterTemperature: Double): String =
        if (waterTemperature != 0.0) "${waterTemperature.roundIfNeeded()}°C" else ""
}