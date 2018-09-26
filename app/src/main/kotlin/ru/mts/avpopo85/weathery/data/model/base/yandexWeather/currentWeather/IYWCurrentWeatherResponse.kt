package ru.mts.avpopo85.weathery.data.model.base.yandexWeather.currentWeather

import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherResponse

interface IYWCurrentWeatherResponse : ICurrentWeatherResponse {

    /**Temperature (°С).*/
    val temperature: Double

    /**What the temperature feels like (°С).*/
    val feelsLikeTemperature: Double

    /**
     * The water temperature (°С).
     *
     * This parameter is returned for localities where this information is relevant.
     */
    val waterTemperature: Double

    /**The code of the weather icon.
     *
     * The icon is available at
     * https://yastatic/weather/i/icons/blueye/color/svg/<value from the icon field>.svg.
     */
    val iconId: String

    /**The code for the weather description.*/
    val weatherDescription: String

    /**Wind speed (meters per second).*/
    val windSpeed: Double

    /**Speed of wind gusts (meters per second).*/
    val windGustsSpeed: Double

    /**Wind direction.*/
    val windDirection: String

    /**Atmospheric pressure (mm Hg).*/
    val atmosphericPressureInMmHg: Double

    /**Atmospheric pressure (hPa).*/
    val atmosphericPressureInhPa: Double

    /**Humidity (percent).*/
    val humidity: Double

    /**Light or dark time of the day.*/
    val daytime: String

    /**Polar day or polar night.*/
    val polar: Boolean

    /**Time of year in this locality.*/
    val season: String

    /**The time when weather data was measured, in Unix time.*/
    val observationUnixTime: Long

    /**Type of precipitation.*/
    val precipitationType: Int

    /**Intensity of precipitation.*/
    val precipitationStrength: Double

    /**Cloud cover.*/
    val cloudiness: Double

}