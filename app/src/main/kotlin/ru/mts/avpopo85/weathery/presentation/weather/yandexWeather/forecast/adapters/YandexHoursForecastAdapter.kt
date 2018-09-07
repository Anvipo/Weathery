package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.HourInfo


class YandexHoursForecastAdapter(private val items: List<HourInfo>) :
    RecyclerView.Adapter<YandexHoursForecastAdapter.Yandex12HoursForecastViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Yandex12HoursForecastViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_yandex_hour_forecast, parent, false)

        return Yandex12HoursForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: Yandex12HoursForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    @Suppress("PrivatePropertyName")
    class Yandex12HoursForecastViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(hourInfo: HourInfo) {
            val hourInLocalValueTV = view.findViewById<TextView>(R.id.hourInLocalValueTV)
            val hourTemperatureValueTV = view.findViewById<TextView>(R.id.hourTemperatureValueTV)
            val feelsLikeHourTemperatureValueTV = view.findViewById<TextView>(R.id.feelsLikeHourTemperatureValueTV)
            val hourConditionValueTV = view.findViewById<TextView>(R.id.hourConditionValueTV)
            val hourWindSpeedValueTV = view.findViewById<TextView>(R.id.hourWindSpeedValueTV)
            val hourWindGustValueTV = view.findViewById<TextView>(R.id.hourWindGustValueTV)
            val hourWindDirectionValueTV = view.findViewById<TextView>(R.id.hourWindDirectionValueTV)
            val hourPressureInMMValueTV = view.findViewById<TextView>(R.id.hourPressureInMMValueTV)
            val hourPressureInPaValueTV = view.findViewById<TextView>(R.id.hourPressureInPaValueTV)
            val hourHumidityInPercentsValueTV = view.findViewById<TextView>(R.id.hourHumidityInPercentsValueTV)
            val hourPrecipitationTypeValueTV = view.findViewById<TextView>(R.id.hourPrecipitationTypeValueTV)
            val hourPrecipitationStrengthValueTV = view.findViewById<TextView>(R.id.hourPrecipitationStrengthValueTV)
            val hourPrecipitationInMmValueTV = view.findViewById<TextView>(R.id.hourPrecipitationInMmValueTV)
            val hourPrecipitationInMinutesValueTV = view.findViewById<TextView>(R.id.hourPrecipitationInMinutesValueTV)
            val hourCloudnessValueTV = view.findViewById<TextView>(R.id.hourCloudnessValueTV)

            hourInfo.let {
                hourInLocalValueTV.text = it.hourInLocalTime
                hourTemperatureValueTV.text = it.temperature.toString()
                feelsLikeHourTemperatureValueTV.text = it.feelsLikeTemperature.toString()
                hourConditionValueTV.text = it.condition
                hourWindSpeedValueTV.text = it.windSpeed.toString()
                hourWindGustValueTV.text = it.windGust.toString()
                hourWindDirectionValueTV.text = it.windDirection
                hourPressureInMMValueTV.text = it.pressureInMM.toString()
                hourPressureInPaValueTV.text = it.pressureInPa.toString()
                hourHumidityInPercentsValueTV.text = it.humidityInPercents.toString()
                hourPrecipitationTypeValueTV.text = it.precipitationType
                hourPrecipitationStrengthValueTV.text = it.precipitationStrength
                hourPrecipitationInMmValueTV.text = it.precipitationInMm.toString()
                hourPrecipitationInMinutesValueTV.text = it.precipitationInMinutes.toString()
                hourCloudnessValueTV.text = it.cloudness
            }
        }
    }
}
