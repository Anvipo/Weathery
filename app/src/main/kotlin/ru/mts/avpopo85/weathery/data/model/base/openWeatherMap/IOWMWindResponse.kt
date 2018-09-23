package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap


interface IOWMWindResponse {

    /**Wind speed.
     *
     *Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.*/
    val speed: Int

    /**Wind direction, degrees (meteorological)*/
    val directionInDegrees: Int

}
