package ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast

@Suppress("PropertyName")
interface IYWPartsResponse {

    /**Nighttime forecast.*/
    val nightForecastResponse: IYWDayTimeResponse?

    /**Morning forecast.*/
    val morningForecastResponse: IYWDayTimeResponse?

    /**Afternoon forecast.*/
    val dayForecastResponse: IYWDayTimeResponse?

    /**Evening forecast.*/
    val eveningForecastResponse: IYWDayTimeResponse?

    /**12-hour forecast for the day.*/
    val _12HoursDayForecastResponse: IYWDayShortResponse?

    /**12-hour forecast for the upcoming night.*/
    val _12HoursNightForecastResponse: IYWDayShortResponse?

}
