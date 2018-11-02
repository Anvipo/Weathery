package ru.mts.avpopo85.weathery.domain.model.base.common

import android.os.Parcelable

interface IForecast : IWeather, Parcelable {

    /**YW: Date of the forecast, in the format YYYY-MM-DD.*/
    val date: String

    val dateInSeconds: Long

}
