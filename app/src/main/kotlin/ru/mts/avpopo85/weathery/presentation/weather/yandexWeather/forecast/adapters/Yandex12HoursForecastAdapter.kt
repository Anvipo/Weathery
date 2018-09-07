package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.DayShort


class Yandex12HoursForecastAdapter(private val items: List<DayShort>) :
    RecyclerView.Adapter<Yandex12HoursForecastAdapter.Yandex12HoursForecastViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Yandex12HoursForecastViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_yandex_12_hours_forecast, parent, false)

        return Yandex12HoursForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: Yandex12HoursForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    @Suppress("PrivatePropertyName")
    class Yandex12HoursForecastViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(parts: DayShort) {
            val titleValueTV = view.findViewById<TextView>(R.id.titleValueTV)
            val temperatureValueTV = view.findViewById<TextView>(R.id.temperatureValueTV)
            val feelsLikeTemperatureValueTV = view.findViewById<TextView>(R.id.feelsLikeTemperatureValueTV)
            val conditionValueTV = view.findViewById<TextView>(R.id.conditionValueTV)
            val windSpeedValueTV = view.findViewById<TextView>(R.id.windSpeedValueTV)
            val windGustValueTV = view.findViewById<TextView>(R.id.windGustValueTV)
            val windDirectionValueTV = view.findViewById<TextView>(R.id.windDirectionValueTV)
            val pressureInMMValueTV = view.findViewById<TextView>(R.id.pressureInMMValueTV)
            val pressureInPaValueTV = view.findViewById<TextView>(R.id.pressureInPaValueTV)
            val humidityInPercentsValueTV = view.findViewById<TextView>(R.id.humidityInPercentsValueTV)
            val precipitationTypeValueTV = view.findViewById<TextView>(R.id.precipitationTypeValueTV)
            val precipitationStrengthValueTV = view.findViewById<TextView>(R.id.precipitationStrengthValueTV)
            val cloudnessValueTV = view.findViewById<TextView>(R.id.cloudnessValueTV)

            parts.let {
                titleValueTV.text = it.title
                temperatureValueTV.text = it.temperature.toString()
                feelsLikeTemperatureValueTV.text = it.feelsLikeTemperature.toString()
                conditionValueTV.text = it.condition
                windSpeedValueTV.text = it.windSpeed.toString()
                windGustValueTV.text = it.windGust.toString()
                windDirectionValueTV.text = it.windDirection
                pressureInMMValueTV.text = it.pressureInMM.toString()
                pressureInPaValueTV.text = it.pressureInPa.toString()
                humidityInPercentsValueTV.text = it.humidityInPercents.toString()
                precipitationTypeValueTV.text = it.precipitationType
                precipitationStrengthValueTV.text = it.precipitationStrength
                cloudnessValueTV.text = it.cloudness
            }
        }
    }
}
