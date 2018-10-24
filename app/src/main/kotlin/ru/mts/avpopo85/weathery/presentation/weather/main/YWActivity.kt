package ru.mts.avpopo85.weathery.presentation.weather.main

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_yw.*
import kotlinx.android.synthetic.main.content_yw.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.AbsBaseActivity
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather.YWCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.YWForecastActivity

class YWActivity : AbsBaseActivity() {

    override val rootLayout: View by lazy { activity_yw_CL }

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
