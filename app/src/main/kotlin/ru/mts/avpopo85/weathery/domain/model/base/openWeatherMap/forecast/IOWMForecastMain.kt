package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.forecast


interface IOWMForecastMain {

    /**Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val temperature: String

    /**Atmospheric pressure on the ground level, hPa*/
    val atmosphericPressureOnTheGroundLevelInhPa: Double

    /**Atmospheric pressure on the sea level, hPa*/
    val atmosphericPressureOnTheSeaLevelInhPa: Double

    /**Humidity, %*/
    val humidity: Int

}
