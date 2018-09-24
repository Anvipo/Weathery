package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.currentWeather


interface IOWMWindResponse {

    /**Wind speed.
     *
     *Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.*/
    val speed: Double

    /**Wind direction, degrees (meteorological)*/
    val directionInDegrees: Double

}
