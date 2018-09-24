package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters

import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWHourInfo


class YWHoursForecastAdapter(private val items: List<IYWHourInfo>) :
    Adapter<YW12HoursForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YW12HoursForecastViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_yw_hour_forecast, parent, false)

        return YW12HoursForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: YW12HoursForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
