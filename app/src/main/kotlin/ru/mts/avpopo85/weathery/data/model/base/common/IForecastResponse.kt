package ru.mts.avpopo85.weathery.data.model.base.common

interface IForecastResponse : IWeatherResponse {

    var saveUnixTime: Long

    /**Date of the forecast, in the format YYYY-MM-DD.*/
    val dateUTC: String

    /**The date of the forecast in Unix time.*/
    val dateInUnixUTCInSeconds: Long

}
