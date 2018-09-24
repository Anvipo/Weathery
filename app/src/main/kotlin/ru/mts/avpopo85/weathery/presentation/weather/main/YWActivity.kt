package ru.mts.avpopo85.weathery.presentation.weather.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_yw.*
import org.jetbrains.anko.startActivity
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather.YWCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.YWForecastActivity

class YWActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yw)

        initButtons()
    }

    private fun initButtons() {

        yw_current_weather_B.setOnClickListener {
            startActivity<YWCurrentWeatherActivity>()
        }

        yw_forecast_B.setOnClickListener {
            startActivity<YWForecastActivity>()
        }

    }

}
