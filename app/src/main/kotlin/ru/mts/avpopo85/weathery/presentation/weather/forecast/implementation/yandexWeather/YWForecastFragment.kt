package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_yw_forecast.yandex_hours_forecast_recycler_view
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWHourInfo
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWParts
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters.YWHoursForecastAdapter
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters.YWTimesOfDayForecastAdapter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastType


class YWForecastFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.item_yw_forecast, container, false) as ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments
            ?.takeIf { it.containsKey(ARG_FORECAST) }
            ?.apply {
                val forecast = getParcelable<YWForecastType>(ARG_FORECAST) ?: return

                fillFields(forecast)

                init12HoursRV(forecast.parts)

                if (forecast.hours != null && forecast.hours.isNotEmpty())
                    initHoursRV(forecast.hours)
                else
                    yandex_hours_forecast_recycler_view.visibility = GONE
            }
    }

    private fun initHoursRV(YWHours: List<IYWHourInfo>) {
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
                YWParts.dayForecast,
                YWParts.eveningForecast,
                YWParts.nightForecast
            )

            adapter = YWTimesOfDayForecastAdapter(timesOfDayForecast)
        }
    }

    private fun fillFields(YWYWForecast: YWForecastType) {
        val moonTextValueTV = view?.findViewById<TextView>(R.id.moonTextValueTV)
        val sunriseTimeValueTV = view?.findViewById<TextView>(R.id.sunriseTimeValueTV)
        val sunsetTimeValueTV = view?.findViewById<TextView>(R.id.sunsetTimeValueTV)

        moonTextValueTV?.text = YWYWForecast.moonText
        sunriseTimeValueTV?.text = YWYWForecast.sunriseInLocalTime
        sunsetTimeValueTV?.text = YWYWForecast.sunsetInLocalTime
    }
}
