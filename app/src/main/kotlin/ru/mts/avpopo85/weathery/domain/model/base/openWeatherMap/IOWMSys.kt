package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap


interface IOWMSys {

    /**Sunrise time, unix, UTC*/
    val sunrise: String

    /**Sunset time, unix, UTC*/
    val sunset: String

}
