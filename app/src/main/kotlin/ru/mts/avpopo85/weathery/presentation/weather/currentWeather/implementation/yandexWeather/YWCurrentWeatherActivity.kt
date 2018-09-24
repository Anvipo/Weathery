package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_yw_current_weather.*
import kotlinx.android.synthetic.main.item_yw_current_weather.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.YandexWeatherModule
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.AbsCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherActivity :
    AbsCurrentWeatherActivity(),
    CurrentWeatherContract.View<YWCurrentWeatherType> {

    @Inject
    lateinit var currentWeatherPresenter: CurrentWeatherContract.Presenter<YWCurrentWeatherType>

    override val progressBar: ProgressBar by lazy { yandex_current_weather_PB }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yw_current_weather)

        App.appComponentForYandexWeather
            .plus(YandexWeatherModule())
            .inject(this)

        currentWeatherPresenter.onBindView(this)
        currentWeatherPresenter.loadCurrentWeather()
    }

    override fun onDestroy() {
        currentWeatherPresenter.onUnbindView()
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    override fun showWeatherResponse(data: YWCurrentWeatherType) {
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
