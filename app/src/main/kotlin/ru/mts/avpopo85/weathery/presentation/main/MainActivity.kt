package ru.mts.avpopo85.weathery.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.currentWeatherBtn
import kotlinx.android.synthetic.main.activity_main.forecastBtn
import org.jetbrains.anko.startActivity
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather.YWCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.YWForecastActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButtons()
    }

    private fun initButtons() {

        currentWeatherBtn.setOnClickListener {
            startActivity<YWCurrentWeatherActivity>()
        }

        forecastBtn.setOnClickListener {
            startActivity<YWForecastActivity>()
        }

    }

}
