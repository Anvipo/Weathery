package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.models.DayTime


class YandexTimesOfDayForecastAdapter(private val items: List<DayTime>) :
    RecyclerView.Adapter<YandexTimeOfDayForecastViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): YandexTimeOfDayForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_yandex_time_of_day_forecast, parent, false)

        return YandexTimeOfDayForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: YandexTimeOfDayForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
