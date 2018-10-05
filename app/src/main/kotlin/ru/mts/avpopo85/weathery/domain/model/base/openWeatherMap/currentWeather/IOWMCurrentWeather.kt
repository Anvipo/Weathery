package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.currentWeather

import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMSys
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWeather
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMWind


interface IOWMCurrentWeather : ICurrentWeather {

    /**more info Weather condition codes*/
    val weather: List<IOWMWeather>

    val main: IOWMCurrentWeatherMain

    /**Visibility, meter*/
    val visibilityInMeters: Int

    val wind: IOWMWind

    val cloudiness: Int

    /**Time of data calculation*/
    val timeOfDataCalculation: String

    val sys: IOWMSys

}