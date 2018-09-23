package ru.mts.avpopo85.weathery.domain.model.base.yandexWeather

import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather

interface IYWCurrentWeather : ICurrentWeather {

    /**Temperature (°С).*/
    override val temperature: String

    /**What the temperature feels like (°С).*/
    val feelsLikeTemperature: String

    /**
     * The water temperature (°С).
     *
     * This parameter is returned for localities where this information is relevant.
     */
    val waterTemperature: String

    /**The url of the weather icon.*/
    val iconUrl: String

    /**Weather description.*/
    val weatherDescription: String

    /**Wind speed (meters per second).*/
    val windSpeed: String

    /**Speed of wind gusts (meters per second).*/
    val windGustsSpeed: String

    /**Wind direction.*/
    val windDirection: String

    /**Atmospheric pressure (mm Hg).*/
    val atmosphericPressureInMmHg: String

    /**Atmospheric pressure (hPa).*/
    val atmosphericPressureInhPa: String

    /**Humidity (percent).*/
    val humidity: String

    /**Light or dark time of the day.*/
    val daytime: String

    /**Polar day or polar night.*/
    val polar: String

    /**Time of year in this locality.*/
    val season: String

    /**The time when weather data was measured, in Unix time.*/
    val observationUnixTime: String

    /**Type of precipitation.*/
    val precipitationType: String

    /**Intensity of precipitation.*/
    val precipitationStrength: String

    /**Cloud cover.*/
    val cloudiness: String

}