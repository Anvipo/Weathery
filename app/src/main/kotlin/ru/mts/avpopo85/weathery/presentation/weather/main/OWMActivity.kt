package ru.mts.avpopo85.weathery.presentation.weather.main

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_owm.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_owm.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.AbsBaseActivity
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.OWMCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.OWMForecastActivity

class OWMActivity : AbsBaseActivity() {

    override val rootLayout: View by lazy { activity_owm_CL }

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
