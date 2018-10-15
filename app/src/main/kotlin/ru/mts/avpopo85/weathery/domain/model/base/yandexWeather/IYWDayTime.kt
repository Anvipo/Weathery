package ru.mts.avpopo85.weathery.domain.model.base.yandexWeather

interface IYWDayTime {

    val title: String

    /**Minimum temperature for the time of day (°C).*/
    val temperatureMinimum: String

    /**Maximum temperature for the time of day (°C).*/
    val temperatureMaximum: String

    /**Average temperature for the time of day (°C).*/
    val temperatureAverage: String

    /**What the temperature feels like (°C).*/
    val feelsLikeTemperature: String

    /**The url of the weather icon.*/
    val iconUrl: String

    /**Weather description.*/
    val condition: String

    /**Light or dark time of the day.*/
    val daytime: String

    /**Polar day or polar night.*/
    val polar: String

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

    /**Predicted amount of precipitation (mm).*/
    val precipitationInMm: String

    /**Predicted duration of precipitation (minutes).*/
    val precipitationInMinutes: String

    /**Type of precipitation.*/
    val precipitationType: String

    /**Intensity of precipitation.*/
    val precipitationStrength: String

    /**Cloud cover.*/
    val cloudiness: String

}
