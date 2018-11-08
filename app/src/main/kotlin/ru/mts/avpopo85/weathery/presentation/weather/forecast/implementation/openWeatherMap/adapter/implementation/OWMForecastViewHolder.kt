package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.implementation

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_owm_forecast.view.dateOwmForecast
import kotlinx.android.synthetic.main.item_owm_forecast.view.descriptionOwmForecast
import kotlinx.android.synthetic.main.item_owm_forecast.view.temperatureOwmForecast
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastViewHolder
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType


class OWMForecastViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view), IForecastViewHolder<OWMForecastType> {

    private val date: TextView = view.dateOwmForecast
    private val description: TextView = view.descriptionOwmForecast
    private val temperature: TextView = view.temperatureOwmForecast

    @SuppressLint("SetTextI18n")
    override fun bind(
        data: OWMForecastType,
        clickListener: (OWMForecastType) -> Unit
    ) {
        date.text = data.date
        description.text = data.weather.description
        temperature.text = "${data.main.temperature}$CELSIUS_DEGREE"

        //todo change color depends on description
//        description.currentTextColor = R.color.

        view.setOnClickListener { clickListener(data) }
    }

}