package ru.mts.avpopo85.weathery.data.utils

import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWCurrentWeatherParameters
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.IYWForecastParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWCurrentWeatherParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWForecastParameters
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.YWConstants

val YW_CURRENT_WEATHER_PARAMETERS: IYWCurrentWeatherParameters by lazy {
    YWCurrentWeatherParameters(
        YWConstants.KRASNODAR_LATITUDE,
        YWConstants.KRASNODAR_LONGITUDE,
        YandexWeatherLanguages.RU_RU.lang_code
    )
}

val YW_FORECAST_PARAMETERS: IYWForecastParameters by lazy {
    YWForecastParameters(
        YWConstants.KRASNODAR_LATITUDE,
        YWConstants.KRASNODAR_LONGITUDE,
        YandexWeatherLanguages.RU_RU.lang_code,
        YWConstants.DAY_NUMBER_IN_FORECAST,
        true,
        true
    )
}