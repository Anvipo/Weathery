package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.item_owm_current_weather.*
import kotlinx.android.synthetic.main.view_owm_current_weather.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMCurrentWeatherModule
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.view.activity.AbsCurrentWeatherActivity
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

//TODO: delete
class OWMCurrentWeatherActivity :
    AbsCurrentWeatherActivity<OWMCurrentWeatherType>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    override lateinit var presenter: CurrentWeatherContract.Presenter<OWMCurrentWeatherType>

    override val swipeRefreshLayout: SwipeRefreshLayout by lazy { item_owm_current_weather_SRL }

    override val rootLayout: CoordinatorLayout by lazy { owm_current_weather_CL }

    override val layoutResID: Int by lazy { R.layout.view_owm_current_weather }

    override val viewToolbar: Toolbar by lazy { toolbar }

    override fun initInjection() {
        App.appComponent
            .plus(OWMCurrentWeatherModule(this))
            .inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun showWeatherResponse(data: OWMCurrentWeatherType) {
        data.let {
            cloudinessValueOWMCWTV.text = "${it.cloudiness}%"
            temperature_value_OWM_CW_TV.text = "${it.temperature} $CELSIUS_DEGREE"
            atmospheric_pressure_value_OWM_CW_TV.text =
                    "${it.atmosphericPressureInhPa} ${getString(R.string.hPa)}"
            humidity_value_OWM_CW_TV.text = "${it.humidity}%"
            suntime_value_OWM_CW_TV.text = it.sys.sunrise + "-" + it.sys.sunset
            visibility_value_OWM_CW_TV.text =
                    "${it.visibilityInMeters} ${getString(R.string.meters)}"
            wind_info_value_OWM_CW_TV.text =
                    "${it.windSpeed} ${getString(R.string.meters_per_second)}, ${it.windDirection}"
            descriptionValueOWMCWTV.text = it.weather.description
        }

        super.showWeatherResponse(data)
    }

}
