package ru.mts.avpopo85.weathery.utils.openWeatherMap

import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.currentWeather.OWMCurrentWeatherResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMForecastResponse
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMListItemResponse
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather.OWMCurrentWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.forecast.OWMForecast

typealias OWMCurrentWeatherResponseType = OWMCurrentWeatherResponse

typealias OWMCurrentWeatherType = OWMCurrentWeather

typealias OWMForecastResponseType = OWMForecastResponse

typealias OWMListItemResponseType = OWMListItemResponse

typealias OWMForecastListResponseType = List<OWMListItemResponseType>

typealias OWMForecastType = OWMForecast

typealias OWMForecastListType = List<OWMForecastType>
