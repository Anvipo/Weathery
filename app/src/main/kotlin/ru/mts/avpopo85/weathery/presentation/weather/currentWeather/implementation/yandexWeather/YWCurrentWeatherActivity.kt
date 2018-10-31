package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.yandexWeather

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_yw_current_weather.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.item_yw_current_weather.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWCurrentWeatherModule
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.view.activity.AbsCurrentWeatherActivity
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherActivity : AbsCurrentWeatherActivity<YWCurrentWeatherType>() {

    @Inject
    override lateinit var presenter: CurrentWeatherContract.Presenter<YWCurrentWeatherType>

    override val swipeRefreshLayout: SwipeRefreshLayout by lazy { item_yw_current_weather_SRL }

    override val rootLayout: CoordinatorLayout by lazy { yw_current_weather_CL }

    override val layoutResID: Int by lazy { R.layout.activity_yw_current_weather }

    override val viewToolbar: Toolbar by lazy { toolbar }

    override fun initInjection() {
        App.appComponent
            .plus(YWCurrentWeatherModule(this))
            .inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun showWeatherResponse(data: YWCurrentWeatherType) {
        super.showWeatherResponse(data)

        data.let {
            temperature_value_YW_CW_TV.text = "${it.temperature} $CELSIUS_DEGREE"
            feels_like_temperature_value_YW_CW_TV.text = "${it.feelsLikeTemperature}$CELSIUS_DEGREE"
            condition_value_YW_CW_TV.text = it.weatherDescription
            wind_info_value_YW_CW_TV.text =
                    "${it.windSpeed} ${getString(R.string.meters_per_second)}${it.windDirection}"
            atmospheric_pressure_in_mm_hg_value_YW_CW_TV.text =
                    "${it.atmosphericPressureInMmHg} ${getString(R.string.mm_hg)}"
            //todo
//            "${it.atmosphericPressureInhPa} ${getString(R.string.hPa)}"
            humidity_value_YW_CW_TV.text = "${it.humidity}%"
            precipitation_type_value_YW_CW_TV.text = it.precipitationType
            precipitation_strength_value_YW_CW_TV.text = it.precipitationStrength
            cloudiness_value_YW_CW_TV.text = it.cloudiness
            daytime_value_YW_CW_TV.text = it.daytime
            polar_value_YW_CW_TV.text = it.polar
            season_value_YW_CW_TV.text = it.season
        }
    }

}
