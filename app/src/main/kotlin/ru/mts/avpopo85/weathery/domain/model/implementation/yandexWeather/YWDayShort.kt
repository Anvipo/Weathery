package ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
/**Object with a 12-hour forecast for the day.*/
data class YWDayShort(

    val title: String,

    /**Highest daytime or lowest nighttime temperature (°C).*/
    val temperature: String,

    /**What the temperature feels like (°C).*/
    val feelsLikeTemperature: String,

    /**The url of the weather icon.*/
    val iconUrl: String,

    /**Weather description.*/
    val condition: String,

    /**Wind speed (meters per second).*/
    val windSpeed: String,

    /**Speed of wind gusts (meters per second).*/
    val windGustsSpeed: String,

    /**Wind direction.*/
    val windDirection: String,

    /**Atmospheric pressure (mm Hg).*/
    val atmosphericPressureInMmHg: String,

    /**Atmospheric pressure (hPa).*/
    val atmosphericPressureInhPa: String,

    /**Humidity (percent).*/
    val humidity: String,

    /**Type of precipitation.*/
    val precipitationType: String,

    /**Intensity of precipitation.*/
    val precipitationStrength: String,

    /**Cloud cover.*/
    val cloudiness: String

) : Parcelable
