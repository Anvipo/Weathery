package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWHourInfo

@Parcelize
/**Object for the hourly forecast. Contains 24 parts.*/
data class YWHourInfo(

    override val hourInLocalTime: String,

    override val hourInUnixTime: String,

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

    override val precipitationInMm: String,

    override val precipitationInMinutes: String,

    override val precipitationType: String,

    override val precipitationStrength: String,

    override val cloudiness: String

) : Parcelable, IYWHourInfo
