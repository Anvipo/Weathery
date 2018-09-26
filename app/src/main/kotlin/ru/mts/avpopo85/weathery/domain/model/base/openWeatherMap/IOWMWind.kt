package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap


interface IOWMWind {

    /**Wind speed.
     *
     *Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.*/
    val speedInUnits: Double

    val direction: String

}
