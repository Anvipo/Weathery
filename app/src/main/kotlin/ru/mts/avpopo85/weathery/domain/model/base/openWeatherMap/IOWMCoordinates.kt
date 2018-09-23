package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap


interface IOWMCoordinates {

    /**City geo location, longitude*/
    val longitude: Double

    /**City geo location, latitude*/
    val latitude: Double

}