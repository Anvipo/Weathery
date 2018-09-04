package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_yandex_current_weather.*
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.App
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.presentation.weather.WeatherContract
import javax.inject.Inject

class YandexCurrentWeatherActivity : AppCompatActivity(), WeatherContract.WeatherView {
    @Inject
    lateinit var yandexCurrentWeatherPresenter: YandexCurrentWeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_current_weather)

        App.appComponentForYandexWeather.plus(YandexWeatherModule()).inject(this)
        yandexCurrentWeatherPresenter.onBindView(this)

        yandexCurrentWeatherPresenter.onStart()
    }

    override fun onDestroy() {
        yandexCurrentWeatherPresenter.onUnbindView()
        super.onDestroy()
    }

    override fun showLoadingProgress() {
        yandex_current_weather_PB.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        yandex_current_weather_PB.visibility = View.GONE
    }

    override fun showWeatherResponse(weatherResponse: String) {
        currentWeatherTV.text = weatherResponse
    }

    override fun showError(throwable: Throwable) {
        val str = throwable.message ?: ""
        longToast(str)
    }
}