package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast

import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.common.OWMCoordinatesResponse

interface IOWMCityResponse {

    /**Number of lines returned by this API call*/
    val country: String

    val coordinates: OWMCoordinatesResponse

    /**City name*/
    val name: String

    /**City ID*/
    val id: Double

}
