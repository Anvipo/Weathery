package ru.mts.avpopo85.weathery.domain.model.base.common

interface IForecast : IWeather {

    /**YW: Date of the forecast, in the format YYYY-MM-DD.*/
    val date: String

}
