package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap


interface IOWMWind {

    /**Wind speed.
     *
     *Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.*/
    val speed: Int

    /**Wind direction, degrees (meteorological)*/
    val directionInDegrees: Int

}
