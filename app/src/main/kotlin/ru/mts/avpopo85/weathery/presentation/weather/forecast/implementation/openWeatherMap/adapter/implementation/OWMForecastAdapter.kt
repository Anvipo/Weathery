package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.implementation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType

class OWMForecastAdapter(override val clickListener: (OWMForecastType) -> Unit) :
    RecyclerView.Adapter<OWMForecastViewHolder>(),
    IForecastAdapter<OWMForecastType> {

    private val items = mutableListOf<OWMForecastType>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OWMForecastViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_owm_forecast, parent, false)

        return OWMForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: OWMForecastViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun addAll(newItems: OWMForecastListType) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}