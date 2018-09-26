package ru.mts.avpopo85.weathery.data.model.base.yandexWeather.common

interface IYWTimeZoneInfo {

    /**Time zone in seconds from UTC.*/
    val offsetFromUTCInSecond: Double

    /**Name of the time zone.*/
    val name: String

    /**Abbreviated name of the time zone.*/
    val abbr: String

    /**Daylight saving time.*/
    val withSummerTime: Boolean

}