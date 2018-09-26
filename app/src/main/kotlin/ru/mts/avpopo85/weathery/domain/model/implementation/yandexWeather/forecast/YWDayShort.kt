package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWDayShort

@Parcelize
/**Object with a 12-hour forecast for the day.*/
data class YWDayShort(

    override val title: String,

    override val temperature: String,

    override val feelsLikeTemperature: String,

    override val iconUrl: String,

    override val condition: String,

    override val windSpeed: String,

    override val windGustsSpeed: String,

    override val windDirection: String,

    override val atmosphericPressureInMmHg: String,

    override val atmosphericPressureInhPa: String,

    override val humidity: String,

    override val precipitationType: String,

    override val precipitationStrength: String,

    override val cloudiness: String

) : Parcelable, IYWDayShort
