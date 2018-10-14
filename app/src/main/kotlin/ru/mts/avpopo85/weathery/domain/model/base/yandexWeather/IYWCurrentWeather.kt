package ru.mts.avpopo85.weathery.domain.model.base.yandexWeather

import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather

interface IYWCurrentWeather : ICurrentWeather {

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

    /**Speed of wind gusts (meters per second).*/
    val windGustsSpeed: String

    /**Atmospheric pressure (mm Hg).*/
    val atmosphericPressureInMmHg: String

    /**Light or dark time of the day.*/
    val daytime: String

    /**Polar day or polar night.*/
    val polar: String

    /**Time of year in this locality.*/
    val season: String

    /**Type of precipitation.*/
    val precipitationType: String

    /**Intensity of precipitation.*/
    val precipitationStrength: String

}