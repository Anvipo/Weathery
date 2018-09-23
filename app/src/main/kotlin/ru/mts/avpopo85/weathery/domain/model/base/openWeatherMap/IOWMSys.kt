package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap


@Suppress("SpellCheckingInspection")
interface IOWMSys {

    /**Internal parameter*/
    val type: Int

    /**Internal parameter*/
    val id: Int

    /**Internal parameter*/
    val message: Double

    /**Country code (GB, JP etc.)*/
    val countryCode: String

    /**Sunrise time, unix, UTC*/
    val sunrise: String

    /**Sunset time, unix, UTC*/
    val sunset: String

}
