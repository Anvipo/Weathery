package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.ForecastResponse

class YandexWeatherAdapter :
        RecyclerView.Adapter<YandexWeatherAdapter.MyViewHolder>() {
    private val items = mutableListOf<ForecastResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_yandex_weather, parent, false) as TextView
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = items[position].toString()
    }

    override fun getItemCount() = items.size

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}