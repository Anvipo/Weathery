package ru.mts.avpopo85.weathery.domain.utils

sealed class MyParsingException(cause: String) : Throwable() {
    override val message: String = cause

    class SeasonParsingException(cause: String) : MyParsingException(cause)
    class PrecipitationTypeParsingException(cause: String) : MyParsingException(cause)
    class PrecipitationStrengthParsingException(cause: String) : MyParsingException(cause)
    class DaytimeParsingException(cause: String) : MyParsingException(cause)
    class CloudinessParsingException(cause: String) : MyParsingException(cause)
    class WeatherDescriptionParsingException(cause: String) : MyParsingException(cause)
    class WindDirectionParsingException(cause: String) : MyParsingException(cause)
    class MoonCodeParsingException(cause: String) : MyParsingException(cause)
    class MoonTextParsingException(cause: String) : MyParsingException(cause)
}