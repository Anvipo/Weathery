package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE
import ru.mts.avpopo85.weathery.presentation.utils.PERCENT_SIGN
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType


class OWMForecastFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.item_owm_forecast, container, false) as ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments
            ?.takeIf { it.containsKey(ARG_FORECAST) }
            ?.apply {
                val forecast = getParcelable<OWMForecastType>(ARG_FORECAST) ?: return

                fillFields(forecast)
            }
    }

    @SuppressLint("SetTextI18n")
    private fun fillFields(forecast: OWMForecastType) {
        val cloudinessValueOWMFTV = view?.findViewById<TextView>(R.id.cloudiness_value_OWM_F_TV)
        val temperatureValueOWMFTV = view?.findViewById<TextView>(R.id.temperature_value_OWM_F_TV)
        val dayTimeValueOWMFTV = view?.findViewById<TextView>(R.id.day_time_value_OWM_F_TV)
        val rainVolumeValueOWMFTV = view?.findViewById<TextView>(R.id.rain_volume_value_OWM_F_TV)
        val weatherDescriptionValueOWMFTV =
            view?.findViewById<TextView>(R.id.weather_description_value_OWM_F_TV)
        val windSpeedValueOWMFTV = view?.findViewById<TextView>(R.id.wind_speed_value_OWM_F_TV)
        val windDirectionValueOWMFTV =
            view?.findViewById<TextView>(R.id.wind_direction_value_OWM_F_TV)
        val atmosphericPressureOnTheGroundLevelInhPaValueOWMFTV =
            view?.findViewById<TextView>(R.id.atmospheric_pressure_on_the_ground_level_in_hPa_value_OWM_F_TV)
        val atmosphericPressureOnTheSeaLevelInhPaValueOWMFTV =
            view?.findViewById<TextView>(R.id.atmospheric_pressure_on_the_sea_level_in_hPa_value_OWM_F_TV)
        val humidityValueOWMFTV = view?.findViewById<TextView>(R.id.humidity_value_OWM_F_TV)

        forecast.let {
            cloudinessValueOWMFTV?.text = "${it.cloudiness}$PERCENT_SIGN"
            dayTimeValueOWMFTV?.text = it.dayTime
            rainVolumeValueOWMFTV?.text =
                    "${it.rainVolumeMm.roundIfNeeded()} ${getString(R.string.mm)}"
            weatherDescriptionValueOWMFTV?.text = it.weather.description
            it.wind.let { (speedInUnits, direction) ->
                //TODO единицы измерения как в запросе
                windSpeedValueOWMFTV?.text = "$speedInUnits м/c"
                windDirectionValueOWMFTV?.text = direction
            }
            it.main.let { (temperature, atmosphericPressureOnTheGroundLevelInhPa, atmosphericPressureOnTheSeaLevelInhPa, humidity) ->
                //TODO конвертация в другие измерения
                atmosphericPressureOnTheGroundLevelInhPaValueOWMFTV?.text =
                        "$atmosphericPressureOnTheGroundLevelInhPa ${getString(R.string.hPa)}"
                atmosphericPressureOnTheSeaLevelInhPaValueOWMFTV?.text =
                        "$atmosphericPressureOnTheSeaLevelInhPa ${getString(R.string.hPa)}"
                humidityValueOWMFTV?.text = "$humidity$PERCENT_SIGN"
                //TODO единицы измерения как в запросе
                temperatureValueOWMFTV?.text = "$temperature $CELSIUS_DEGREE"
            }

        }
    }
}
