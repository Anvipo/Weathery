package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap


interface IOWMCoordinatesResponse {

    /**City geo location, longitude*/
    val longitude: Double

    /**City geo location, latitude*/
    val latitude: Double

}