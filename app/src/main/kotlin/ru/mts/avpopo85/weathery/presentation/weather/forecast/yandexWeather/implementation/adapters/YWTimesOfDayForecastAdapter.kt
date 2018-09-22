package ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.implementation.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWDayTime


class YWTimesOfDayForecastAdapter(private val items: List<YWDayTime>) :
    RecyclerView.Adapter<YWTimeOfDayForecastViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): YWTimeOfDayForecastViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_yandex_time_of_day_forecast, parent, false)

        return YWTimeOfDayForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: YWTimeOfDayForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
