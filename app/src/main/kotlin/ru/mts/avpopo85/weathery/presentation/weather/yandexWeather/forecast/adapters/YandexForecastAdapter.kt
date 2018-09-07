package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.Forecast


class YandexForecastAdapter :
    RecyclerView.Adapter<YandexForecastAdapter.YandexForecastAdapterViewHolder>() {
    private val items = mutableListOf<Forecast>()

    fun addItems(item: List<Forecast>) {
        items += item
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
        //        private val morningForecastValueTV = view.findViewById(R.id.morningForecastValueTV) as TextView
//        private val dayForecastValueTV = view.findViewById(R.id.dayForecastValueTV) as TextView
//        private val eveningForecastValueTV =
//            view.findViewById(R.id.eveningForecastValueTV) as TextView
//        private val nightForecastValueTV = view.findViewById(R.id.nightForecastValueTV) as TextView
        fun bind(forecast: Forecast) {
            fillFields(forecast)

            initRV(forecast)

//            _12HoursNightForecastValueTV.text = forecast.parts._12HoursNightForecast.toString()
//            morningForecastValueTV.text = forecast.parts.morningForecast.toString()
//            dayForecastValueTV.text = forecast.parts.dayForecast.toString()
//            eveningForecastValueTV.text = forecast.parts.eveningForecast.toString()
//            nightForecastValueTV.text = forecast.parts.nightForecast.toString()
        }

        private fun initRV(forecast: Forecast) {
            @Suppress("LocalVariableName")
            val yandex_12_hours_forecast_recycler_view =
                view.findViewById<RecyclerView>(R.id.yandex_12_hours_forecast_recycler_view)

            yandex_12_hours_forecast_recycler_view.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)

                val forecastParts = listOf(
                    forecast.parts._12HoursDayForecast,
                    forecast.parts._12HoursNightForecast
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