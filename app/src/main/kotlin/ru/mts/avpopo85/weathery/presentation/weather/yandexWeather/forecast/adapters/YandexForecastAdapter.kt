package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.Forecast
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.HourInfo
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.Parts


class YandexForecastAdapter :
    RecyclerView.Adapter<YandexForecastAdapter.YandexForecastAdapterViewHolder>() {
    private val items = mutableListOf<Forecast>()

    fun addItems(items: List<Forecast>) {
        this.items += items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): YandexForecastAdapterViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_yandex_forecast, parent, false)

        return YandexForecastAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: YandexForecastAdapterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    @Suppress("PrivatePropertyName")
    class YandexForecastAdapterViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(forecast: Forecast) {
            fillFields(forecast)

            init12HoursRV(forecast.parts)
            if (forecast.hours != null)
                initHoursRV(forecast.hours)
        }

        private fun initHoursRV(hours: List<HourInfo>) {
            val yandexHoursForecastRV =
                view.findViewById<RecyclerView>(R.id.yandex_hours_forecast_recycler_view)

            yandexHoursForecastRV.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)

                adapter = YandexHoursForecastAdapter(hours)
            }
        }

        private fun init12HoursRV(parts: Parts) {
            val yandex12HoursForecastRecyclerView =
                view.findViewById<RecyclerView>(R.id.yandex_12_hours_forecast_recycler_view)

            yandex12HoursForecastRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)

                val forecastParts = listOf(
                    parts._12HoursDayForecast,
                    parts._12HoursNightForecast
                )

                adapter = Yandex12HoursForecastAdapter(forecastParts)
            }
        }

        private fun fillFields(forecast: Forecast) {
            val dateTsValueTV = view.findViewById<TextView>(R.id.dateTsValueTV)
            val moonTextValueTV = view.findViewById<TextView>(R.id.moonTextValueTV)
            val sunriseTimeValueTV = view.findViewById<TextView>(R.id.sunriseTimeValueTV)
            val sunsetTimeValueTV = view.findViewById<TextView>(R.id.sunsetTimeValueTV)
            val weekSerialNumberValueTV =
                view.findViewById<TextView>(R.id.weekSerialNumberValueTV)

            dateTsValueTV.text = forecast.date_ts
            moonTextValueTV.text = forecast.moonText
            sunriseTimeValueTV.text = forecast.sunriseInLocalTime
            sunsetTimeValueTV.text = forecast.sunsetInLocalTime
            weekSerialNumberValueTV.text = forecast.weekSerialNumber.toString()
        }

        /*private fun fill12HoursDayForecast(view: View, data: DayShort) {
            val temperatureValueTV = view.findViewById<TextView>(R.id.dayTemperatureValueTV)
            val feelsLikeTemperatureValueTV =
                view.findViewById<TextView>(R.id.feelsLikeDayTemperatureValueTV)
            val conditionValueTV = view.findViewById<TextView>(R.id.dayConditionValueTV)
            val windSpeedValueTV = view.findViewById<TextView>(R.id.dayWindSpeedValueTV)
            val windGustValueTV = view.findViewById<TextView>(R.id.dayWindGustValueTV)
            val windDirectionValueTV = view.findViewById<TextView>(R.id.dayWindDirectionValueTV)
            val pressureInMMValueTV = view.findViewById<TextView>(R.id.pressureInMMValueTV)
            val pressureInPaValueTV = view.findViewById<TextView>(R.id.pressureInPaValueTV)
            val humidityInPercentsValueTV =
                view.findViewById<TextView>(R.id.humidityInPercentsValueTV)
            val precipitationTypeValueTV =
                view.findViewById<TextView>(R.id.precipitationTypeValueTV)
            val precipitationStrengthValueTV =
                view.findViewById<TextView>(R.id.precipitationStrengthValueTV)
            val cloudnessValueTV = view.findViewById<TextView>(R.id.cloudnessValueTV)

            data.let {
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

        private fun fill12HoursNightForecast(view: View, data: DayShort) {
            val temperatureValueTV = view.findViewById<TextView>(R.id.dayTemperatureValueTV)
            val feelsLikeTemperatureValueTV =
                view.findViewById<TextView>(R.id.feelsLikeDayTemperatureValueTV)
            val conditionValueTV = view.findViewById<TextView>(R.id.dayConditionValueTV)
            val windSpeedValueTV = view.findViewById<TextView>(R.id.dayWindSpeedValueTV)
            val windGustValueTV = view.findViewById<TextView>(R.id.dayWindGustValueTV)
            val windDirectionValueTV = view.findViewById<TextView>(R.id.dayWindDirectionValueTV)
            val pressureInMMValueTV = view.findViewById<TextView>(R.id.pressureInMMValueTV)
            val pressureInPaValueTV = view.findViewById<TextView>(R.id.pressureInPaValueTV)
            val humidityInPercentsValueTV =
                view.findViewById<TextView>(R.id.humidityInPercentsValueTV)
            val precipitationTypeValueTV =
                view.findViewById<TextView>(R.id.precipitationTypeValueTV)
            val precipitationStrengthValueTV =
                view.findViewById<TextView>(R.id.precipitationStrengthValueTV)
            val cloudnessValueTV = view.findViewById<TextView>(R.id.cloudnessValueTV)

            data.let {
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
        }*/
    }
}