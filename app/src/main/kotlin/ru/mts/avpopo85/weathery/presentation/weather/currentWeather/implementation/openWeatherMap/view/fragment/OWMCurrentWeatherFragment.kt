package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.item_owm_current_weather.*
import kotlinx.android.synthetic.main.view_owm_current_weather.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMCurrentWeatherModule
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.view.fragment.AbsCurrentWeatherFragment
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherFragment : AbsCurrentWeatherFragment<OWMCurrentWeatherType>(),
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    override lateinit var presenter: CurrentWeatherContract.Presenter<OWMCurrentWeatherType>

    override val viewToolbar: ActionBar by lazy { (activity as AppCompatActivity).supportActionBar!! }

    override val swipeRefreshLayout: SwipeRefreshLayout by lazy { item_owm_current_weather_SRL }

    override val hidingLayout: View by lazy { item_owm_current_weather_SV }

    override val rootLayout: View by lazy { owm_current_weather_CL }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.view_owm_current_weather, container, false)

    override fun initInjection() {
        App.appComponent
            .plus(OWMCurrentWeatherModule(this.context!!))
            .inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun showWeatherResponse(data: OWMCurrentWeatherType) {
        data.let {
            cloudinessOwmCurrentWeather.text = "${it.cloudiness}%"
            temperatureOwmCurrentWeather.text = "${it.temperature} $CELSIUS_DEGREE"
            atmosphericPressureOwmCurrentWeather.text =
                    "${it.atmosphericPressureInhPa} ${getString(R.string.hPa)}"
            humidityOwmCurrentWeather.text = "${it.humidity}%"
            suntimeOwmCurrentWeather.text = "${it.sys.sunrise}-${it.sys.sunset}"
            visibilityOwmCurrentWeather.text =
                    "${it.visibilityInMeters} ${getString(R.string.meters)}"
            windInfoOwmCurrentWeather.text =
                    "${it.windSpeed} ${getString(R.string.meters_per_second)}, ${it.windDirection}"
            descriptionOwmCurrentWeather.text = it.weather.description
        }

        super.showWeatherResponse(data)
    }

}