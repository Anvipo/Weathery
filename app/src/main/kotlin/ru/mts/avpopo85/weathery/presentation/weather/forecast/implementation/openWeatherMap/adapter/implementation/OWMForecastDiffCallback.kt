package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.implementation

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType


class OWMForecastDiffCallback(
    private val newData: OWMForecastListType,
    private val oldData: OWMForecastListType
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

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