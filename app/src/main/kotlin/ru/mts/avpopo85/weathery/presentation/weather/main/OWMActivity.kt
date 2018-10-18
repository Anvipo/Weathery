package ru.mts.avpopo85.weathery.presentation.weather.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_owm.*
import org.jetbrains.anko.startActivity
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.OWMCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.OWMForecastActivity

class OWMActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owm)

        toolbar.title = getString(R.string.forecast_type_choice)
        setSupportActionBar(toolbar)

        initButtons()
    }

    private fun initButtons() {

        owm_current_weather_B.setOnClickListener {
            startActivity<OWMCurrentWeatherActivity>()
        }

        owm_forecast_B.setOnClickListener {
            startActivity<OWMForecastActivity>()
        }

    }

}
