package ru.mts.avpopo85.weathery.domain.model.base.common

interface ICurrentWeather : IWeather {

    /**OWM: Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    /**YW: Temperature (°С).*/
    val temperature: String

    /**OWM: Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa*/
    val atmosphericPressureInhPa: String

    /**%*/
    val humidity: String

    val timeOfDataCalculation: String

    /**Cloud cover.*/
    val cloudiness: String

    /**YW: Wind speed (meters per second).*/
    /**OWM: Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.*/
    val windSpeed: String

    /**Wind direction.*/
    val windDirection: String

}
