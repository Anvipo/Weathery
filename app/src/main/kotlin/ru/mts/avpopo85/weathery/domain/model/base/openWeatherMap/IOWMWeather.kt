package ru.mts.avpopo85.weathery.domain.model.base.openWeatherMap

//https://openweathermap.org/weather-conditions
interface IOWMWeather {

    /**Weather condition id*/
    val conditionCode: Int

    /**Group of weather parameters (Rain, Snow, Extreme etc.)*/
    val groupOfWeatherParameters: String

    /**Weather condition within the group*/
    val description: String

    /**Weather icon cityID*/
    val icon: String

}