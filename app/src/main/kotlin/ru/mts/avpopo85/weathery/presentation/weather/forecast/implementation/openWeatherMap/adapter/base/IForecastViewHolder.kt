package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base

import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast

interface IForecastViewHolder<T : IForecast> {

    fun bind(data: T, clickListener: (T) -> Unit)

}
