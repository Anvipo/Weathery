package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.currentWeather


@Suppress("SpellCheckingInspection")
interface IOWMSysResponse {

    /**Internal parameter*/
    val type: Int

    /**Internal parameter*/
    val id: Int

    /**Internal parameter*/
    val message: Double

    /**Country code (GB, JP etc.)*/
    val countryCode: String

    /**Sunrise time, unix, UTC*/
    val sunrise: Int

    /**Sunset time, unix, UTC*/
    val sunset: Int

}
