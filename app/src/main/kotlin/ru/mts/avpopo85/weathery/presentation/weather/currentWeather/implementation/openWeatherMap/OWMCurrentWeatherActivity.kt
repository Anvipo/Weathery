package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_owm_current_weather.*
import kotlinx.android.synthetic.main.item_owm_current_weather.*
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.OpenWeatherMapModule
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.utils.DEGREE
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.AbsCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherActivity :
    AbsCurrentWeatherActivity(),
    CurrentWeatherContract.View<OWMCurrentWeatherType> {

    @Inject
    lateinit var currentWeatherPresenter: CurrentWeatherContract.Presenter<OWMCurrentWeatherType>

    override val progressBar: ProgressBar by lazy { owm_current_weather_PB }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owm_current_weather)

        App.appComponentForYandexWeather
            .plus(OpenWeatherMapModule())
            .inject(this)

        hideLayout()

        currentWeatherPresenter.onBindView(this)

        currentWeatherPresenter.loadCurrentWeather()
    }

    override fun hideLayout() {
        item_owm_current_weather.visibility = View.GONE
    }

    override fun onDestroy() {
        currentWeatherPresenter.onUnbindView()
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    override fun showWeatherResponse(data: OWMCurrentWeatherType) {
        showLayout()

        data.let {
            observationUnixTimeValueOWMCWTV.text = it.timeOfDataCalculation
            cloudinessValueOWMCWTV.text = "${it.clouds.cloudiness}%"
            temperatureValueOWMCWTV.text = "${it.main.temperature} $CELSIUS_DEGREE"
            atmosphericPressureValueOWMCWTV.text =
                    "${it.main.atmosphericPressureInhPa} ${getString(R.string.hPa)}"
            humidityValueOWMCWTV.text = "${it.main.humidity}%"
            sunriseValueOWMCWTV.text = it.sys.sunrise
            sunsetValueOWMCWTV.text = it.sys.sunset
            visibilityValueOWMCWTV.text = "${it.visibilityInMeters} ${getString(R.string.meters)}"
            windSpeedValueOWMCWTV.text =
                    "${it.wind.speedInUnits} ${getString(R.string.meters_per_second)}"
            windDirectionValueOWMCWTV.text = "${it.wind.directionInDegrees}$DEGREE"

            val onlyOne = it.weather.size == 1

            if (onlyOne) {
                val description = it.weather.first().description

                descriptionValueOWMCWTV.text = description
            } else {
                longToast("${this::class.java.simpleName}.showWeatherResponse - data.weather.size > 1")
            }
        }
    }

    override fun showLayout() {
        item_owm_current_weather.visibility = View.VISIBLE
    }

}
