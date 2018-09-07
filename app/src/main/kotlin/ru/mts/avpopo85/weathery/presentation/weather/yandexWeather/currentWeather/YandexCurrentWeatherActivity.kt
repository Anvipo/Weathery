package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_yandex_current_weather.*
import kotlinx.android.synthetic.main.item_yandex_current_weather.*
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.CurrentWeather
import javax.inject.Inject

class YandexCurrentWeatherActivity : AppCompatActivity(),
    CurrentWeatherContract.CurrentWeatherView {
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

    override fun showWeatherResponse(data: CurrentWeather) {
        data.let {
            temperatureValueCWTV.text = it.temperature.toString()
            feelsLikeTemperatureValueCWTV.text = it.feelsLikeTemperature.toString()
            conditionValueCWTV.text = it.weatherDescription
            windSpeedValueCWTV.text = it.windSpeed.toString()
            windGustValueCWTV.text = it.windGust.toString()
            windDirectionValueCWTV.text = it.windDirection
            pressureInMMValueCWTV.text = it.pressureInMmHg.toString()
            pressureInPaValueCWTV.text = it.pressureInMmHg.toString()
            humidityInPercentsValueCWTV.text = it.humidityInPercents.toString()
            precipitationTypeValueCWTV.text = it.precipitationType
            precipitationStrengthValueCWTV.text = it.precipitationStrength
            cloudnessValueCWTV.text = it.cloudness
            daytimeValueCWTV.text = it.daytime
            polarValueCWTV.text = it.polar
            seasonValueCWTV.text = it.season
            observationUnixTimeValueCWTV.text = it.observationUnixTime
        }
    }

    override fun showError(throwable: Throwable) {
        longToast(throwable.message ?: "")
    }

    override fun showError(message: String) {
        longToast(message)
    }
}