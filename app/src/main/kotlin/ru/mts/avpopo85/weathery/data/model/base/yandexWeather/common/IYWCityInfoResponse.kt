package ru.mts.avpopo85.weathery.data.model.base.yandexWeather.common

import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.common.YWTimeZoneInfo

interface IYWCityInfoResponse {

    /**The latitude (in degrees).*/
    val latitude: Double

    /**The longitude (in degrees).*/
    val longitude: Double

    /**Information about the time zone.*/
    val timeZoneInfo: YWTimeZoneInfo

    /**The normal pressure for the given coordinates (mm Hg).*/
    val normalPressureMmHg: Double

    /**The normal pressure for the given coordinates (hPa).*/
    val pressureNormInPa: Double

    /**Locality page on yandex.weather.*/
    val localityUrlPath: String

}