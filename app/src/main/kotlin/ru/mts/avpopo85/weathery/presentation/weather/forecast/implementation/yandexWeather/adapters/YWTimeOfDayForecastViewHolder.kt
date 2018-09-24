package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.TextView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWDayTime
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE

class YWTimeOfDayForecastViewHolder(private val view: View) : ViewHolder(view) {
    @SuppressLint("SetTextI18n")

    fun bind(YWDayTime: IYWDayTime) {
        val minimumTemperatureValueToDTV =
            view.findViewById<TextView>(R.id.minimumTemperatureValueToDTV)
        val averageTemperatureValueToDTV =
            view.findViewById<TextView>(R.id.averageTemperatureValueToDTV)
        val maximumTemperatureValueToDTV =
            view.findViewById<TextView>(R.id.maximumTemperatureValueToDTV)
        val feelsLikeTemperatureValueToDTV =
            view.findViewById<TextView>(R.id.feelsLikeTemperatureValueToDTV)
        val conditionValueToDTV = view.findViewById<TextView>(R.id.conditionValueToDTV)
        val windSpeedValueToDTV = view.findViewById<TextView>(R.id.windSpeedValueToDTV)
        val windGustValueToDTV = view.findViewById<TextView>(R.id.windGustValueToDTV)
        val windDirectionValueToDTV = view.findViewById<TextView>(R.id.windDirectionValueToDTV)
        val pressureInMmHgValueToDTV = view.findViewById<TextView>(R.id.pressureInMmValueToDTV)
        val pressureInhPaValueToDTV = view.findViewById<TextView>(R.id.pressureInPaValueToDTV)
        val humidityInPercentsValueToDTV =
            view.findViewById<TextView>(R.id.humidityInPercentsValueToDTV)
        val precipitationTypeValueToDTV =
            view.findViewById<TextView>(R.id.precipitationTypeValueToDTV)
        val precipitationStrengthValueToDTV =
            view.findViewById<TextView>(R.id.precipitationStrengthValueToDTV)
        val precipitationInMmValueTV = view.findViewById<TextView>(R.id.precipitationInMmValueTV)
        val precipitationInMinutesValueTV =
            view.findViewById<TextView>(R.id.precipitationInMinutesValueTV)
        val daytimeValueToDTV = view.findViewById<TextView>(R.id.daytimeValueToDTV)
        val polarValueToDTV = view.findViewById<TextView>(R.id.polarValueToDTV)
        val cloudinessValueToDTV = view.findViewById<TextView>(R.id.cloudinessValueToDTV)
        val titleValueToDTV = view.findViewById<TextView>(R.id.titleValueToDTV)

        YWDayTime.let {
            titleValueToDTV.text = it.title
            minimumTemperatureValueToDTV.text = "${it.temperatureMinimum}$CELSIUS_DEGREE"
            averageTemperatureValueToDTV.text = "${it.temperatureAverage}$CELSIUS_DEGREE"
            maximumTemperatureValueToDTV.text = "${it.temperatureMaximum}$CELSIUS_DEGREE"
            feelsLikeTemperatureValueToDTV.text = "${it.feelsLikeTemperature}$CELSIUS_DEGREE"
            conditionValueToDTV.text = it.condition
            daytimeValueToDTV.text = it.daytime
            polarValueToDTV.text = it.polar
            windSpeedValueToDTV.text =
                    "${it.windSpeed} ${view.context.getString(R.string.meters_per_second)}"
            windGustValueToDTV.text =
                    "${it.windGustsSpeed} ${view.context.getString(R.string.meters_per_second)}"
            windDirectionValueToDTV.text = it.windDirection
            pressureInhPaValueToDTV.text =
                    "${it.atmosphericPressureInhPa} ${view.context.getString(R.string.hPa)}"
            pressureInMmHgValueToDTV.text =
                    "${it.atmosphericPressureInMmHg} ${view.context.getString(R.string.mm_hg)}"
            humidityInPercentsValueToDTV.text = "${it.humidity}%"
            precipitationInMinutesValueTV.text =
                    "${it.precipitationInMinutes} ${view.context.getString(R.string.minutes)}"
            precipitationInMmValueTV.text =
                    "${it.precipitationInMm} ${view.context.getString(R.string.mm)}"
            precipitationStrengthValueToDTV.text = it.precipitationStrength
            precipitationTypeValueToDTV.text = it.precipitationType
            cloudinessValueToDTV.text = it.cloudiness
        }
    }

}