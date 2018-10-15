package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.currentWeather

import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap.IOWMSys
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather


interface IOWMCurrentWeather : ICurrentWeather {

    /**more info Weather condition codes*/
    val weather: OWMWeather

    /**Visibility, meter*/
    val visibilityInMeters: Int

    val sys: IOWMSys

}
