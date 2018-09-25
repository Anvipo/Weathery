package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap

import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather


interface IOWMCurrentWeather : ICurrentWeather {

    val coordinates: IOWMCoordinates

    /**more info Weather condition codes*/
    val weather: List<IOWMWeather>

    /**Internal parameter*/
    val base: String

    val main: IOWMMain

    /**Visibility, meter*/
    val visibilityInMeters: Int

    val wind: IOWMWind

    val clouds: IOWMClouds

    /**Time of data calculation*/
    val timeOfDataCalculation: String

    val sys: IOWMSys

    /**City ID*/
    val cityID: Int

    /**City name*/
    val cityName: String

    /**Internal parameter*/
    val code: Int

}