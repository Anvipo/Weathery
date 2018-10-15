package ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast

interface IYWHourInfoResponse {

    /**The hour the forecast is for (0-23) using the local time.*/
    val hourInLocalTime: String

    /**The time of the forecast in Unix time.*/
    val hourInUnixTime: Long

    /**Temperature (°C).*/
    val temperature: Double

    /**What the temperature feels like (°C).*/
    val feelsLikeTemperature: Double

    /**The code of the weather icon.
     *
     * The icon is available at
     * https://yastatic.net/weather/i/icons/blueye/color/svg/<value from the icon field>.svg.
     * */
    val iconId: String

    /**The code for the weather description.*/
    val condition: String

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
