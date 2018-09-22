package ru.mts.avpopo85.weathery.utils

import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWCurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWForecastResponse
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWCurrentWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWForecast

typealias CurrentWeatherType = YWCurrentWeather

typealias CurrentWeatherResponseType = YWCurrentWeatherResponse

typealias ForecastType = YWForecast

typealias ForecastListType = List<YWForecast>

typealias ForecastResponseType = YWForecastResponse

typealias ForecastListResponseType = List<YWForecastResponse>
