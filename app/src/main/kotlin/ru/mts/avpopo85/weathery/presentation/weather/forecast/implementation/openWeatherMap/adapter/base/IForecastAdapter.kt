package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast

interface IForecastAdapter<T : IForecast> {

    val clickListener: (T) -> Unit

    fun updateData(newData: List<T>)

}
