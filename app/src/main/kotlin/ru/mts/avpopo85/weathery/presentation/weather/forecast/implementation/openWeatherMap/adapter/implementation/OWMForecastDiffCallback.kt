package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.implementation

import android.os.Bundle
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.BaseDiff
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType


class OWMForecastDiffCallback(
    private val newData: OWMForecastListType,
    private val oldData: OWMForecastListType
) : BaseDiff<OWMForecastType>(newData, oldData) {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition].date == newData[newItemPosition].date

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldData[oldItemPosition] == newData[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        val newProduct = newData[newItemPosition]
//        val oldProduct = oldData[oldItemPosition]

        val diffBundle = Bundle()

        //todo
        //my realization

        return if (diffBundle.size() == 0) null else diffBundle
    }

}