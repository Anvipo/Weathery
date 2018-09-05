package ru.mts.avpopo85.weathery.domain.weather.yandexWeather

import ru.mts.avpopo85.weathery.utils.MyParsingException.*

object YandexWeatherMapper {
    fun getCloudnessString(cloudness: Double): String = when (cloudness) {
        0.0 -> "ясно"
        0.25 -> "малооблачно"
        0.5, 0.75 -> "облачно с прояснениями"
        1.0 -> "пасмурно"
        else -> throw CloudnessParsingException("Неизвестное значение параметра: $cloudness")
    }

    fun getWeatherDescriptionString(season: String): String = when (season) {
        "clear" -> "ясно"
        "partly-cloudy" -> "малооблачно"
        "cloudy" -> "облачно с прояснениями"
        "overcast" -> "пасмурно"
        "partly-cloudy-and-light-rain", "cloudy-and-light-rain", "overcast-and-light-rain" -> "небольшой дождь"
        "partly-cloudy-and-rain", "cloudy-and-rain" -> "дождь"
        "overcast-and-rain" -> "сильный дождь"
        "overcast-thunderstorms-with-rain" -> "сильный дождь, гроза"
        "overcast-and-wet-snow" -> "дождь со снегом"
        "partly-cloudy-and-light-snow", "cloudy-and-light-snow", "overcast-and-light-snow" -> "небольшой снег"
        "partly-cloudy-and-snow", "cloudy-and-snow" -> "снег"
        "overcast-and-snow" -> "снегопад"
        else -> throw WeatherDescriptionParsingException("Неизвестное значение параметра: $season")
    }

    fun getPrecipitationStrengthString(precStrength: Double): String = when (precStrength) {
        0.0 -> "без осадков"
        0.25 -> "слабые осадки"
        0.5 -> "средние осадки"
        0.75 -> "сильные осадки"
        1.0 -> "очень сильные осадки"
        else -> throw PrecipitationStrengthParsingException("Неизвестное значение параметра: $precStrength")
    }

    fun getPrecipitationTypeString(precType: Int): String = when (precType) {
        0 -> "без осадков"
        1 -> "дождь"
        2 -> "дождь со снегом"
        3 -> "снег"
        else -> throw PrecipitationTypeParsingException("Неизвестное значение параметра: $precType")
    }

    fun getWindDirectionString(windDirection: String): String = when (windDirection) {
        "nw" -> "северо-западное"
        "n" -> "северное"
        "ne" -> "северо-восточное"
        "e" -> "восточное"
        "se" -> "юго-восточное"
        "s" -> "южное"
        "sw" -> "юго-западное"
        "w" -> "западное"
        "c" -> "штиль"
        else -> throw WindDirectionParsingException("Неизвестное значение параметра: $windDirection")
    }

    fun getDaytimeString(daytime: String): String = when (daytime) {
        "d" -> "светлое"
        "n" -> "тёмное"
        else -> throw DaytimeParsingException("Неизвестное значение параметра: $daytime")
    }

    fun getPolarString(polar: Boolean): String = if (polar) "да" else "нет"

    fun getSeasonString(season: String): String = when (season) {
        "summer" -> "лето"
        "autumn" -> "осень"
        "winter" -> "зима"
        "spring" -> "весна"
        else -> throw SeasonParsingException("Неизвестное значение параметра: $season")
    }

    fun getWaterTemperatureString(waterTemperature: Double): String =
        if (waterTemperature != 0.0) "$waterTemperature°C" else ""

    fun getMoonCodeString(moonCode: Int): String = when (moonCode) {
        8 -> "новолуние"
        12 -> "первая четверть"
        in 9..11, in 13..15 -> "растущая Луна"
        0 -> "полнолуние"
        in 1..3, in 5..7 -> "убывающая Луна"
        4 -> "последняя четверть"
        else -> throw MoonCodeParsingException("Неизвестное значение параметра: $moonCode")
    }

    fun getMoonTextString(moonText: String): String = when (moonText) {
        "new-moon" -> "новолуние"
        "first-quarter" -> "первая четверть"
        "growing-moon" -> "растущая Луна"
        "full-moon" -> "полнолуние"
        "decreasing-moon" -> "убывающая Луна"
        "last-quarter" -> "последняя четверть"
        else -> throw MoonTextParsingException("Неизвестное значение параметра: $moonText")
    }
}