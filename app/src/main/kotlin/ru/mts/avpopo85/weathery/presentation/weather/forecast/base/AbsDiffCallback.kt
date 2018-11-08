package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import androidx.recyclerview.widget.DiffUtil
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast

abstract class BaseDiff<T : IForecast>(
    private val newData: List<T>,
    private val oldData: List<T>
) : DiffUtil.Callback() {

    override fun getNewListSize(): Int = newData.size

    override fun getOldListSize(): Int = oldData.size

}