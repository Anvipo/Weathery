package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_yandex_forecast_activity.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapters.YandexForecastAdapter

//TODO ViewPager
/*
class YandexForecastFragment : Fragment() {
    lateinit var yandexForecastAdapter: YandexForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_yandex_forecast_activity,
            container,
            false
        )

        yandexForecastAdapter = YandexForecastAdapter()

        yandex_forecast_recycler_view.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(context)



            adapter = yandexForecastAdapter
        }

        return view
    }
}*/
