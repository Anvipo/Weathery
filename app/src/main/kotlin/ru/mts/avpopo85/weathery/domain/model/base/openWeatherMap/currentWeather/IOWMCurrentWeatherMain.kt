package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.currentWeather

interface IOWMCurrentWeatherMain {

    /**Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.*/
    val temperature: String

    /**Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa*/
    val atmosphericPressureInhPa: String

    /**Humidity, %*/
    val humidity: Int

}