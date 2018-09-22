package ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.implementation

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
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWForecast
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWHourInfo
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWParts
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.implementation.adapters.YWHoursForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.implementation.adapters.YWTimesOfDayForecastAdapter


class YWForecastFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.item_yandex_forecast, container, false) as ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments
            ?.takeIf { it.containsKey(ARG_FORECAST) }
            ?.apply {
                val forecast = getParcelable<YWForecast>(ARG_FORECAST) ?: return

                fillFields(forecast)

                init12HoursRV(forecast.YWParts)

                if (forecast.YWHours != null && forecast.YWHours.isNotEmpty())
                    initHoursRV(forecast.YWHours)
                else
                    yandex_hours_forecast_recycler_view.visibility = GONE
            }
    }

    private fun initHoursRV(YWHours: List<YWHourInfo>) {
        val yandexHoursForecastRV =
            view?.findViewById<RecyclerView>(R.id.yandex_hours_forecast_recycler_view)

        yandexHoursForecastRV?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = YWHoursForecastAdapter(YWHours)
        }
    }

    private fun init12HoursRV(YWParts: YWParts) {
        val yandex12HoursForecastRecyclerView =
            view?.findViewById<RecyclerView>(R.id.yandex_12_hours_forecast_recycler_view)

        yandex12HoursForecastRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)

            val timesOfDayForecast = listOf(
                YWParts.morningForecast,
                YWParts.YWDayForecast,
                YWParts.eveningForecast,
                YWParts.nightForecast
            )

            adapter = YWTimesOfDayForecastAdapter(timesOfDayForecast)
        }
    }

    private fun fillFields(YWForecast: YWForecast) {
        val moonTextValueTV = view?.findViewById<TextView>(R.id.moonTextValueTV)
        val sunriseTimeValueTV = view?.findViewById<TextView>(R.id.sunriseTimeValueTV)
        val sunsetTimeValueTV = view?.findViewById<TextView>(R.id.sunsetTimeValueTV)

        moonTextValueTV?.text = YWForecast.moonText
        sunriseTimeValueTV?.text = YWForecast.sunriseInLocalTime
        sunsetTimeValueTV?.text = YWForecast.sunsetInLocalTime
    }
}
