package ru.mts.avpopo85.weathery.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather.YandexCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.YandexForecastActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButtons()
    }

    private fun initButtons() {
        /*locateButton.setOnClickListener {
            startActivity<MapActivity>()
        }*/

        /*openWeatherBtn.setOnClickListener {
            startActivity<OpenWeatherActivity>()
        }*/

        currentWeatherBtn.setOnClickListener {
            startActivity<YandexCurrentWeatherActivity>()
        }

        forecastBtn.setOnClickListener {
            startActivity<YandexForecastActivity>()
        }

        /*settingsBtn.setOnClickListener {
            startActivity<SettingsActivity>()
        }*/
    }

}
