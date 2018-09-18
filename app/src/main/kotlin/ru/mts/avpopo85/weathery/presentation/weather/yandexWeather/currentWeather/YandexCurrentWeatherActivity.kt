package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.currentWeather

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_yandex_current_weather.*
import kotlinx.android.synthetic.main.item_yandex_current_weather.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.domain.models.CurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.WeatherActivity
import ru.mts.avpopo85.weathery.utils.CELSIUS_DEGREE
import javax.inject.Inject

class YandexCurrentWeatherActivity : WeatherActivity(), CurrentWeatherContract.View {

    override val progressBar: ProgressBar by lazy { yandex_current_weather_PB }

    @Inject
    lateinit var presenter: CurrentWeatherContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_current_weather)

        App.appComponentForYandexWeather.plus(YandexWeatherModule()).inject(this)

        presenter.onBindView(this)
        presenter.onStart()
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
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

}
