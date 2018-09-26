package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.currentWeather

interface IOWMCurrentWeatherMain {

    /**Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val temperature: Double

    /**Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa*/
    val atmosphericPressureInhPa: Int

    /**Humidity, %*/
    val humidity: Int

    /**Minimum temperature at the moment.
     *
     *This is deviation from current temperature
     *that is possible for large cities and megalopolises geographically expanded
     *(use these parameter optionally).
     *
     *Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val minimumTemperature: Double

    /**Maximum temperature at the moment.
     *
     *This is deviation from current temperature
     *that is possible for large cities and megalopolises geographically expanded
     *(use these parameter optionally).
     *
     *Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val maximumTemperature: Double

}