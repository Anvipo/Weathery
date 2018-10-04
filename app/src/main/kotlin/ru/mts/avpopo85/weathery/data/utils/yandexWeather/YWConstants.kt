package ru.mts.avpopo85.weathery.data.utils.yandexWeather

import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.currentWeather.IYWCurrentWeatherParameters
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast.IYWForecastParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.currentWeather.YWCurrentWeatherParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.forecast.YWForecastParameters

@Suppress("unused")
object YWConstants {

    const val BASE_URL: String = "http://api.weather.yandex.ru/v1/"

    const val API_KEY: String = "c927ab88-f126-4d38-bebb-8857c4520861"

    private const val KRASNODAR_LATITUDE: Double = 45.03

    private const val KRASNODAR_LONGITUDE: Double = 38.98

    private const val DAY_NUMBER_IN_FORECAST: Int = 11

    val YW_CURRENT_WEATHER_PARAMETERS: IYWCurrentWeatherParameters by lazy {
        YWCurrentWeatherParameters(
            KRASNODAR_LATITUDE,
            KRASNODAR_LONGITUDE,
            YandexWeatherLanguages.RU_RU.languageCode
        )
    }

    val YW_FORECAST_PARAMETERS: IYWForecastParameters by lazy {
        YWForecastParameters(
            KRASNODAR_LATITUDE,
            KRASNODAR_LONGITUDE,
            YandexWeatherLanguages.RU_RU.languageCode,
            DAY_NUMBER_IN_FORECAST,
            true,
            true
        )
    }

}