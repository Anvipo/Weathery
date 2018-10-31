package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.implementation

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_owm_forecast.view.*
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastViewHolder
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType


class OWMForecastViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view), IForecastViewHolder<OWMForecastType> {

    private val dateTV: TextView = view.date_owm_forecast_item_TV
    private val descriptionTV: TextView = view.description_owm_forecast_item_TV
    private val temperatureTV: TextView = view.temperature_owm_forecast_item_TV

    @SuppressLint("SetTextI18n")
    override fun bind(
        data: OWMForecastType,
        clickListener: (OWMForecastType) -> Unit
    ) {
        dateTV.text = data.date
        descriptionTV.text = data.weather.description
        temperatureTV.text = "${data.main.temperature}$CELSIUS_DEGREE"

        view.setOnClickListener { clickListener(data) }
    }

}