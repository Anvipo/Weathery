package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_yandex_current_weather.yandex_current_weather_PB
import kotlinx.android.synthetic.main.item_yandex_current_weather.*
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.CurrentWeather
import ru.mts.avpopo85.weathery.utils.CELSIUS_DEGREE
import javax.inject.Inject

class YandexCurrentWeatherActivity : AppCompatActivity(),
    CurrentWeatherContract.CurrentWeatherView {
    override val context: Context = this

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

    @SuppressLint("SetTextI18n")
    override fun showWeatherResponse(data: CurrentWeather) {
        data.let {
            temperatureValueCWTV.text = "${it.temperature} $CELSIUS_DEGREE"
            feelsLikeTemperatureValueCWTV.text = "${it.feelsLikeTemperature}$CELSIUS_DEGREE"
            conditionValueCWTV.text = it.weatherDescription
            windSpeedValueCWTV.text = "${it.windSpeed} ${getString(R.string.meters_per_second)}"
            windGustsSpeedValueCWTV.text =
                    "${it.windGustsSpeed} ${getString(R.string.meters_per_second)}"
            windDirectionValueCWTV.text = it.windDirection
            atmosphericPressureInMmHgValueCWTV.text =
                    "${it.atmosphericPressureInMmHg} ${getString(R.string.mm_hg)}"
            atmosphericPressureInhPaValueCWTV.text =
                    "${it.atmosphericPressureInhPa} ${getString(R.string.hPa)}"
            humidityValueCWTV.text = "${it.humidity}%"
            precipitationTypeValueCWTV.text = it.precipitationType
            precipitationStrengthValueCWTV.text = it.precipitationStrength
            cloudinessValueCWTV.text = it.cloudiness
            daytimeValueCWTV.text = it.daytime
            polarValueCWTV.text = it.polar
            seasonValueCWTV.text = it.season
            observationUnixTimeValueCWTV.text = it.observationUnixTime
        }
    }

    override fun showError(throwable: Throwable) {
        longToast(throwable.message ?: "")
    }

    override fun showError(message: String?) {
        if (message != null)
            longToast(message)
    }
}