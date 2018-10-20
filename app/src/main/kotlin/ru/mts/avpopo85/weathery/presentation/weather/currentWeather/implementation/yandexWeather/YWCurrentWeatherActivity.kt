package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ScrollView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlinx.android.synthetic.main.activity_yw_current_weather.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.item_yw_current_weather.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWCurrentWeatherModule
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.AbsCurrentWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherActivity : AbsCurrentWeatherActivity<YWCurrentWeatherType>() {

    @Inject
    lateinit var presenter: CurrentWeatherContract.Presenter<YWCurrentWeatherType>

    override val view: ScrollView by lazy { item_yw_current_weather }

    override val rootLayout: CoordinatorLayout by lazy { yw_current_weather_CL }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yw_current_weather)

        toolbar.title = getString(R.string.current_weather)

        setSupportActionBar(toolbar)

        App.appComponent
            .plus(YWCurrentWeatherModule(this))
            .inject(this)

        presenter.onBindView(this)
        presenter.loadCurrentWeather()
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    override fun showWeatherResponse(data: YWCurrentWeatherType) {
        showLayout()

        data.let {
            temperature_value_YW_CW_TV.text = "${it.temperature} $CELSIUS_DEGREE"
            feels_like_temperature_value_YW_CW_TV.text = "${it.feelsLikeTemperature}$CELSIUS_DEGREE"
            condition_value_YW_CW_TV.text = it.weatherDescription
            wind_speed_value_YW_CW_TV.text =
                    "${it.windSpeed} ${getString(R.string.meters_per_second)}"
            wind_gusts_speed_value_YW_CW_TV.text =
                    "${it.windGustsSpeed} ${getString(R.string.meters_per_second)}"
            wind_direction_value_YW_CW_TV.text = it.windDirection
            atmospheric_pressure_in_mm_hg_value_YW_CW_TV.text =
                    "${it.atmosphericPressureInMmHg} ${getString(R.string.mm_hg)}"
            atmospheric_pressure_in_hPa_value_YW_CW_TV.text =
                    "${it.atmosphericPressureInhPa} ${getString(R.string.hPa)}"
            humidity_value_YW_CW_TV.text = "${it.humidity}%"
            precipitation_type_value_YW_CW_TV.text = it.precipitationType
            precipitation_strength_value_YW_CW_TV.text = it.precipitationStrength
            cloudiness_value_YW_CW_TV.text = it.cloudiness
            daytime_value_YW_CW_TV.text = it.daytime
            polar_value_YW_CW_TV.text = it.polar
            season_value_YW_CW_TV.text = it.season
            observation_unix_time_value_YW_CW_TV.text = it.timeOfDataCalculation
        }
    }

    override fun changeTitle(title: String) {
        toolbar.title = title
    }

}
