package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_yandex_forecast.yandex_hours_forecast_recycler_view
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.Forecast
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.HourInfo
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.Parts
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapters.YandexHoursForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapters.YandexTimesOfDayForecastAdapter
import ru.mts.avpopo85.weathery.utils.ARG_FORECAST


class YandexForecastFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.item_yandex_forecast, container, false) as ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(ARG_FORECAST) }?.apply {
            val forecast = getParcelable<Forecast>(ARG_FORECAST) ?: return

            fillFields(forecast)

            init12HoursRV(forecast.parts)

            if (forecast.hours != null && forecast.hours.isNotEmpty())
                initHoursRV(forecast.hours)
            else
                yandex_hours_forecast_recycler_view.visibility = GONE
        }
    }

    private fun initHoursRV(hours: List<HourInfo>) {
        val yandexHoursForecastRV =
            view?.findViewById<RecyclerView>(R.id.yandex_hours_forecast_recycler_view)

        yandexHoursForecastRV?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = YandexHoursForecastAdapter(hours)
        }
    }

    private fun init12HoursRV(parts: Parts) {
        val yandex12HoursForecastRecyclerView =
            view?.findViewById<RecyclerView>(R.id.yandex_12_hours_forecast_recycler_view)

        yandex12HoursForecastRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)

            val timesOfDayForecast = listOf(
                parts.morningForecast,
                parts.dayForecast,
                parts.eveningForecast,
                parts.nightForecast
            )

            adapter = YandexTimesOfDayForecastAdapter(timesOfDayForecast)
        }
    }

    private fun fillFields(forecast: Forecast) {
        val moonTextValueTV = view?.findViewById<TextView>(R.id.moonTextValueTV)
        val sunriseTimeValueTV = view?.findViewById<TextView>(R.id.sunriseTimeValueTV)
        val sunsetTimeValueTV = view?.findViewById<TextView>(R.id.sunsetTimeValueTV)

        moonTextValueTV?.text = forecast.moonText
        sunriseTimeValueTV?.text = forecast.sunriseInLocalTime
        sunsetTimeValueTV?.text = forecast.sunsetInLocalTime
    }
}
