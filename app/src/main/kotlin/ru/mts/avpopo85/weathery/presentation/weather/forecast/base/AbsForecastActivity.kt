package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import android.os.Bundle
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter

abstract class AbsForecastActivity<T : IForecast> :
    AbsWeatherActivity(),
    ForecastContract.View<T> {

    abstract val clickListener: (T) -> Unit

    protected abstract val mAdapter: IForecastAdapter<T>

    protected abstract fun fillRecyclerView(data: List<T>)

    protected abstract fun initRecyclerView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
    }
}
