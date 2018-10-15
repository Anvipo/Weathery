package ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast

interface IYWDayTimeResponse {

    /**Minimum temperature for the time of day (°C).*/
    val temperatureMinimum: Double

    /**Maximum temperature for the time of day (°C).*/
    val temperatureMaximum: Double

    /**Average temperature for the time of day (°C).*/
    val temperatureAverage: Double

    /**What the temperature feels like (°C).*/
    val feelsLikeTemperature: Double

    /**The code of the weather icon.
     *
     * The icon is available at
     * https://yastatic.net/weather/i/icons/blueye/color/svg/<value from the icon field>.svg.
     */
    val iconId: String

    /**The code for the weather description.*/
    val condition: String

    /**Light or dark time of the day.*/
    val daytime: String

    /**Polar day or polar night.*/
    val polar: Boolean

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

    /**Predicted amount of precipitation (mm).*/
    val precipitationInMm: Double

    /**Predicted duration of precipitation (minutes).*/
    val precipitationInMinutes: Double

    /**Type of precipitation.*/
    val precipitationType: Int

    /**Intensity of precipitation.*/
    val precipitationStrength: Double

    /**Cloud cover.*/
    val cloudiness: Double

}
