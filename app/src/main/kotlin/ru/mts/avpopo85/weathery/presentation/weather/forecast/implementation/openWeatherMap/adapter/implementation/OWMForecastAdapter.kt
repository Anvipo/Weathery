package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.implementation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.BaseRecyclerAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType

class OWMForecastAdapter(override val itemClickListener: (OWMForecastType) -> Unit) :
    BaseRecyclerAdapter<OWMForecastViewHolder>(),
    IForecastAdapter<OWMForecastType> {

    var data: OWMForecastListType
        get() = _data
        set(value) {
            updateData(value)
        }

    override fun updateData(newData: OWMForecastListType) {
        val diffCallback = OWMForecastDiffCallback(newData = newData, oldData = data)

        //for large data sets it is advised to move the calculation to background thread
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        _data.clear()
        _data.addAll(newData)

        diffResult.dispatchUpdatesTo(listUpdateCallback)
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]

        return when (item.weather.conditionCode) {
            in 200..299 -> {
                //it's thunderstorm
                R.layout.item_owm_forecast
            }
            in 300..399 -> {
                //it's drizzle
                R.layout.item_owm_forecast
            }
            in 500..599 -> {
                //it's rain
                R.layout.item_owm_forecast
            }
            in 600..699 -> {
                //it's snow
                R.layout.item_owm_forecast
            }
            in 700..799 -> {
                //it's atmosphere
                R.layout.item_owm_forecast
            }
            800 -> {
                //it's clear
                R.layout.item_owm_forecast
            }
            in 801..899 -> {
                //it's clouds
                R.layout.item_owm_forecast
            }
            else -> {
                R.layout.item_owm_forecast
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OWMForecastViewHolder {
        //USE viewType ONLY IF OVERRIDED getItemViewType(position: Int): Int
        val view = LayoutInflater
            .from(parent.context)
            .inflate(viewType, parent, false)

        return OWMForecastViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: OWMForecastViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        //example: https://proandroiddev.com/diffutil-is-a-must-797502bc1149
        if (payloads.isEmpty()) {
            //DONT CHANGE
            //calls onBindViewHolder(holder: VH, position: Int)
            super.onBindViewHolder(holder, position, payloads)
        } else {
            onHavePayloads(payloads)
        }
    }

    override fun onBindViewHolder(holder: OWMForecastViewHolder, position: Int) {
        holder.bind(data = data[position], clickListener = itemClickListener)
    }

    override fun getItemCount(): Int = data.size

    private val _data = mutableListOf<OWMForecastType>()

    private fun onHavePayloads(payloads: List<Any>) {
        for (payload in payloads) {
            //my realization
        }
    }

}