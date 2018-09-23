package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWDayTime

@Parcelize
/**Object with the weather forecast for the night*/
data class YWDayTime(

    override val title: String,

    override val temperatureMinimum: String,

    override val temperatureMaximum: String,

    override val temperatureAverage: String,

    override val feelsLikeTemperature: String,

    override val iconUrl: String,

    override val condition: String,

    override val daytime: String,

    override val polar: String,

    override val windSpeed: String,

    override val windGustsSpeed: String,

    override val windDirection: String,

    override val atmosphericPressureInMmHg: String,

    override val atmosphericPressureInhPa: String,

    override val humidity: String,

    override val precipitationInMm: String,

    override val precipitationInMinutes: String,

    override val precipitationType: String,

    override val precipitationStrength: String,

    override val cloudiness: String

) : Parcelable, IYWDayTime
