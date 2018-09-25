package ru.mts.avpopo85.weathery.utils.openWeatherMap

import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather.OWMCurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMListItemResponse
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.OWMCurrentWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.forecast.OWMForecast

typealias OWMCurrentWeatherResponseType = OWMCurrentWeatherResponse

typealias OWMCurrentWeatherType = OWMCurrentWeather

typealias OWMForecastResponseType = OWMListItemResponse

typealias OWMForecastListResponseType = List<OWMForecastResponseType>

typealias OWMForecastType = OWMForecast

typealias OWMForecastListType = List<OWMForecastType>
