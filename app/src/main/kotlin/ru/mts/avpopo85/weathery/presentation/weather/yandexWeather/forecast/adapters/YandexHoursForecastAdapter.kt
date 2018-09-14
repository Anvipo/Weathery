package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.HourInfo
import ru.mts.avpopo85.weathery.utils.CELSIUS_DEGREE


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

    override fun getItemCount(): Int = items.size

    class Yandex12HoursForecastViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(hourInfo: HourInfo) {
            val hourInLocalValueTV = view.findViewById<TextView>(R.id.hourInLocalValueTV)
            val hourTemperatureValueTV = view.findViewById<TextView>(R.id.hourTemperatureValueTV)
            val feelsLikeHourTemperatureValueTV =
                view.findViewById<TextView>(R.id.feelsLikeHourTemperatureValueTV)
            val hourConditionValueTV = view.findViewById<TextView>(R.id.hourConditionValueTV)
            val hourWindSpeedValueTV = view.findViewById<TextView>(R.id.hourWindSpeedValueTV)
            val hourWindGustValueTV = view.findViewById<TextView>(R.id.hourWindGustValueTV)
            val hourWindDirectionValueTV =
                view.findViewById<TextView>(R.id.hourWindDirectionValueTV)
            val hourPressureInMmHgValueTV =
                view.findViewById<TextView>(R.id.hourPressureInMMValueTV)
            val hourPressureInhPaValueTV = view.findViewById<TextView>(R.id.hourPressureInPaValueTV)
            val hourHumidityInPercentsValueTV =
                view.findViewById<TextView>(R.id.hourHumidityInPercentsValueTV)
            val hourPrecipitationTypeValueTV =
                view.findViewById<TextView>(R.id.hourPrecipitationTypeValueTV)
            val hourPrecipitationStrengthValueTV =
                view.findViewById<TextView>(R.id.hourPrecipitationStrengthValueTV)
            val hourPrecipitationInMmValueTV =
                view.findViewById<TextView>(R.id.hourPrecipitationInMmValueTV)
            val hourPrecipitationInMinutesValueTV =
                view.findViewById<TextView>(R.id.hourPrecipitationInMinutesValueTV)
            val hourCloudinessValueTV = view.findViewById<TextView>(R.id.hourCloudinessValueTV)

            hourInfo.let {
                hourInLocalValueTV.text = it.hourInLocalTime
                hourTemperatureValueTV.text = "${it.temperature}$CELSIUS_DEGREE"
                feelsLikeHourTemperatureValueTV.text = "${it.feelsLikeTemperature}$CELSIUS_DEGREE"
                hourConditionValueTV.text = it.condition
                hourWindSpeedValueTV.text =
                        "${it.windSpeed} ${view.context.getString(R.string.meters_per_second)}"
                hourWindGustValueTV.text =
                        "${it.windGustsSpeed} ${view.context.getString(R.string.meters_per_second)}"
                hourWindDirectionValueTV.text = it.windDirection
                hourPressureInMmHgValueTV.text =
                        "${it.atmosphericPressureInMmHg} ${view.context.getString(R.string.mm_hg)}"
                hourPressureInhPaValueTV.text =
                        "${it.atmosphericPressureInhPa} ${view.context.getString(R.string.hPa)}"
                hourHumidityInPercentsValueTV.text = "${it.humidity}%"
                hourPrecipitationTypeValueTV.text = it.precipitationType
                hourPrecipitationStrengthValueTV.text = it.precipitationStrength
                hourPrecipitationInMmValueTV.text =
                        "${it.precipitationInMm} ${view.context.getString(R.string.mm)}"
                hourPrecipitationInMinutesValueTV.text =
                        "${it.precipitationInMinutes} ${view.context.getString(R.string.minutes)}"
                hourCloudinessValueTV.text = it.cloudiness
            }
        }
    }
}
