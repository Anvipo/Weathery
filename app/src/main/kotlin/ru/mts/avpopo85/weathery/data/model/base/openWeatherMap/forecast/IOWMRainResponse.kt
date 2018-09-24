package ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast

@Suppress("PropertyName")
interface IOWMRainResponse {

    /**Rain volume for last 3 hours, mm*/
    val rainVolumeForLast3hoursMm: Double

}
