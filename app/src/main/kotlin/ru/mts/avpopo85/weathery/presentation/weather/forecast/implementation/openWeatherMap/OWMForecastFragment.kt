package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        val cloudinessValueOWMFTV = view?.findViewById<TextView>(R.id.cloudinessValueOWMFTV)
        val temperatureValueOWMFTV = view?.findViewById<TextView>(R.id.temperatureValueOWMFTV)
        val dayTimeValueOWMFTV = view?.findViewById<TextView>(R.id.dayTimeValueOWMFTV)
        val rainVolumeValueOWMFTV = view?.findViewById<TextView>(R.id.rainVolumeValueOWMFTV)
        val weatherDescriptionValueOWMFTV =
            view?.findViewById<TextView>(R.id.weatherDescriptionValueOWMFTV)
        val windSpeedValueOWMFTV = view?.findViewById<TextView>(R.id.windSpeedValueOWMFTV)
        val windDirectionValueOWMFTV = view?.findViewById<TextView>(R.id.windDirectionValueOWMFTV)
        val atmosphericPressureOnTheGroundLevelInhPaValueOWMFTV =
            view?.findViewById<TextView>(R.id.atmosphericPressureOnTheGroundLevelInhPaValueOWMFTV)
        val atmosphericPressureOnTheSeaLevelInhPaValueOWMFTV =
            view?.findViewById<TextView>(R.id.atmosphericPressureOnTheSeaLevelInhPaValueOWMFTV)
        val humidityValueOWMFTV = view?.findViewById<TextView>(R.id.humidityValueOWMFTV)

        forecast.let {
            cloudinessValueOWMFTV?.text = "${it.cloudiness}$PERCENT_SIGN"
            //TODO единицы измерения как в запросе
            temperatureValueOWMFTV?.text = "${it.main.temperature} $CELSIUS_DEGREE"
            dayTimeValueOWMFTV?.text = it.dayTime
            rainVolumeValueOWMFTV?.text =
                    "${it.rainVolumeMm.roundIfNeeded()} ${getString(R.string.mm)}"
            weatherDescriptionValueOWMFTV?.text = it.weather.description
            it.wind.let { (speedInUnits, direction) ->
                //TODO единицы измерения как в запросе
                windSpeedValueOWMFTV?.text = "$speedInUnits м/c"
                windDirectionValueOWMFTV?.text = direction
            }
            it.main.let { (
                                  _,
                                  atmosphericPressureOnTheGroundLevelInhPa,
                                  _,
                                  atmosphericPressureOnTheSeaLevelInhPa,
                                  humidity
                          ) ->
                //TODO конверция в другие измерения
                atmosphericPressureOnTheGroundLevelInhPaValueOWMFTV?.text =
                        "$atmosphericPressureOnTheGroundLevelInhPa ${getString(R.string.hPa)}"
                atmosphericPressureOnTheSeaLevelInhPaValueOWMFTV?.text =
                        "$atmosphericPressureOnTheSeaLevelInhPa ${getString(R.string.hPa)}"
                humidityValueOWMFTV?.text = "$humidity$PERCENT_SIGN"
            }

        }
    }
}
