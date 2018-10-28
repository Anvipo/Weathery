package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.subitem_owm_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.presentation.base.fragment.AbsBaseFragment
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.utils.PERCENT_SIGN
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.utils.FORECAST_INFO_INTENT_TAG
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType

@Suppress("unused")
class OWMForecastInfoFragment : AbsBaseFragment() {

    override val rootLayout: View by lazy { subitem_owm_forecast_SV }

    val viewToolbar: ActionBar by lazy { (activity as AppCompatActivity).supportActionBar!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.view_owm_forecast_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewToolbar.title = getString(R.string.forecast)

        val forecastInfo =
            arguments?.getParcelable<OWMForecastType>(FORECAST_INFO_INTENT_TAG) ?: return

        fillFields(forecastInfo)
    }

    override fun changeTitle(title: String) {
        viewToolbar.title = title
    }

    @SuppressLint("SetTextI18n")
    private fun fillFields(forecastInfo: OWMForecastType) {
        forecastInfo.let {
            changeTitle(it.cityName)

            date_value_OWM_F_TV.text = it.date
            cloudiness_value_OWM_F_TV.text = "${it.cloudiness}$PERCENT_SIGN"
            day_time_value_OWM_F_TV.text = it.dayTime
            rain_volume_value_OWM_F_TV.text =
                    "${it.rainVolumeMm.roundIfNeeded()} ${getString(R.string.mm)}"
            weather_description_value_OWM_F_TV.text = it.weather.description
            //TODO единицы измерения как в запросе
            wind_speed_value_OWM_F_TV.text =
                    "${it.windSpeed} ${getString(R.string.meters_per_second)}"
            wind_direction_value_OWM_F_TV.text = it.windDirection
            it.main.let { (
                                  temperature,
                                  atmosphericPressureOnTheGroundLevelInhPa,
                                  atmosphericPressureOnTheSeaLevelInhPa,
                                  humidity) ->
                //TODO конвертация в другие измерения
                val hPa = getString(R.string.hPa)

                atmospheric_pressure_on_the_ground_level_in_hPa_value_OWM_F_TV.text =
                        "$atmosphericPressureOnTheGroundLevelInhPa $hPa"
                atmospheric_pressure_on_the_sea_level_in_hPa_value_OWM_F_TV.text =
                        "$atmosphericPressureOnTheSeaLevelInhPa $hPa"
                humidity_value_OWM_F_TV.text = "$humidity$PERCENT_SIGN"
                //TODO единицы измерения как в запросе
                temperature_value_OWM_F_TV.text = "$temperature $CELSIUS_DEGREE"
            }
        }

    }

}