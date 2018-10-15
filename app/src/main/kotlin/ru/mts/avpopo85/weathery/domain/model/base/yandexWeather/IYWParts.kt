package ru.mts.avpopo85.weathery.domain.model.base.yandexWeather

@Suppress("PropertyName")
interface IYWParts {

    /**Nighttime forecast.*/
    val nightForecast: IYWDayTime

    /**Morning forecast.*/
    val morningForecast: IYWDayTime

    /**Afternoon forecast.*/
    val dayForecast: IYWDayTime

    /**Evening forecast.*/
    val eveningForecast: IYWDayTime

    /**12-hour forecast for the day.*/
    val _12HourForecastForDay: IYWDayShort

    /**12-hour forecast for the upcoming night.*/
    val _12HourForecastForNight: IYWDayShort

}
