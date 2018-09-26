package ru.mts.avpopo85.weathery.utils.yandexWeather

import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.currentWeather.YWCurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.forecast.YWForecastResponse
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.currentWeather.YWCurrentWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast.YWForecast

typealias YWCurrentWeatherType = YWCurrentWeather

typealias YWCurrentWeatherResponseType = YWCurrentWeatherResponse

typealias YWForecastType = YWForecast

typealias YWForecastListType = List<YWForecastType>

typealias YWForecastResponseType = YWForecastResponse

typealias YWForecastListResponseType = List<YWForecastResponseType>
