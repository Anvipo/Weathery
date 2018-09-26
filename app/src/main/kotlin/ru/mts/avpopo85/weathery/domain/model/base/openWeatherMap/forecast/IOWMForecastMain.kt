package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.forecast


interface IOWMForecastMain {

    /**Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val temperature: Double

    /**Atmospheric pressure on the ground level, hPa*/
    val atmosphericPressureOnTheGroundLevelInhPa: Double

    /**Atmospheric pressure on the sea level by default, hPa*/
    val atmosphericPressureOnTheSeaLevelByDefaultInhPa: Double

    /**Atmospheric pressure on the sea level, hPa*/
    val atmosphericPressureOnTheSeaLevelInhPa: Double

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