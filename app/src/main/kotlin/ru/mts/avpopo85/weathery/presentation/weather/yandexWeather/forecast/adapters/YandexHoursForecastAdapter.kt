package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapters

import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.models.HourInfo


class YandexHoursForecastAdapter(private val items: List<HourInfo>) :
    Adapter<Yandex12HoursForecastViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): Yandex12HoursForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_yandex_hour_forecast, parent, false)

        return Yandex12HoursForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: Yandex12HoursForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
