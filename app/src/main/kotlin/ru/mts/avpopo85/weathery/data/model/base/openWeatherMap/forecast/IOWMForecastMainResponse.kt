package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast

interface IOWMForecastMainResponse {

    /**Temperature.
     *
     *Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val temperature: Double

    /**Minimum temperature at the moment of calculation.
     *
     *This is deviation from 'temperature' that is possible
     *for large cities and megalopolises geographically expanded (use these parameter optionally).
     *
     *Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val minimumTemperature: Double

    /**Atmospheric pressure on the ground level, hPa*/
    val atmosphericPressureOnTheGroundLevelInhPa: Double

    /**Internal parameter*/
    val tempKf: Double

    /**Humidity, %*/
    val humidity: Int

    /**Atmospheric pressure on the sea level by default, hPa*/
    val atmosphericPressureOnTheSeaLevelByDefaultInhPa: Double

    /**Atmospheric pressure on the sea level, hPa*/
    val atmosphericPressureOnTheSeaLevelInhPa: Double

    /**Maximum temperature at the moment of calculation.
     *
     *This is deviation from 'temperature' that is possible
     *for large cities and megalopolises geographically expanded (use these parameter optionally).
     *
     *Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val maximumTemperature: Double

}
