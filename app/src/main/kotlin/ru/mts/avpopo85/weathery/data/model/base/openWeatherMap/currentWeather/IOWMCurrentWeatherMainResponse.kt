package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.currentWeather


interface IOWMCurrentWeatherMainResponse {

    /**Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val temperature: Double

    /**Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa*/
    val atmosphericPressureInhPa: Double

    /**Humidity, %*/
    val humidity: Double

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