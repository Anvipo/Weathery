package ru.mts.avpopo85.weathery.utils

import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWCurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWForecastResponse
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWCurrentWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWForecast

typealias YWCurrentWeatherType = YWCurrentWeather

typealias YWCurrentWeatherResponseType = YWCurrentWeatherResponse

typealias YWForecastType = YWForecast

typealias YWForecastListType = List<YWForecastType>

typealias YWForecastResponseType = YWForecastResponse

typealias YWForecastListResponseType = List<YWForecastResponseType>
