package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_yw_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWHourInfo
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast.YWParts
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
                    hours_YW_F_RV.visibility = GONE
            }
    }

    private fun initHoursRV(YWHours: List<IYWHourInfo>) {
        val yandexHoursForecastRV =
            view?.findViewById<RecyclerView>(R.id.hours_YW_F_RV)

        yandexHoursForecastRV?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = YWHoursForecastAdapter(YWHours)
        }
    }

    private fun init12HoursRV(YWParts: YWParts) {
        val yandex12HoursForecastRecyclerView =
            view?.findViewById<RecyclerView>(R.id._12_hours_YW_F_RV)

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
        val moonTextValueTV = view?.findViewById<TextView>(R.id.moon_text_value_YW_F_TV)
        val sunriseTimeValueTV = view?.findViewById<TextView>(R.id.sunrise_time_value_YW_F_TV)
        val sunsetTimeValueTV = view?.findViewById<TextView>(R.id.sunset_time_value_YW_F_TV)

        moonTextValueTV?.text = YWYWForecast.moonText
        sunriseTimeValueTV?.text = YWYWForecast.sunriseInLocalTime
        sunsetTimeValueTV?.text = YWYWForecast.sunsetInLocalTime
    }
}
