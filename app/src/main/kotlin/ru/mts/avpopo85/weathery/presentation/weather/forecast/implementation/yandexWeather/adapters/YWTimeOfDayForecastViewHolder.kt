package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.TextView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWDayTime
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE

class YWTimeOfDayForecastViewHolder(private val view: View) : ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(YWDayTime: IYWDayTime) {
        val minimumTemperatureValueToDTV =
            view.findViewById<TextView>(R.id.minimum_temperature_value_YW_ToD_TV)
        val averageTemperatureValueToDTV =
            view.findViewById<TextView>(R.id.average_temperature_value_YW_ToD_TV)
        val maximumTemperatureValueToDTV =
            view.findViewById<TextView>(R.id.maximum_temperature_value_YW_ToD_TV)
        val feelsLikeTemperatureValueToDTV =
            view.findViewById<TextView>(R.id.feels_like_temperature_value_YW_ToD_TV)
        val conditionValueToDTV = view.findViewById<TextView>(R.id.condition_value_YW_ToD_TV)
        val windSpeedValueToDTV = view.findViewById<TextView>(R.id.wind_speed_value_YW_ToD_TV)
        val windGustValueToDTV = view.findViewById<TextView>(R.id.wind_gust_value_YW_ToD_TV)
        val windDirectionValueToDTV =
            view.findViewById<TextView>(R.id.wind_direction_value_YW_ToD_TV)
        val pressureInMmHgValueToDTV =
            view.findViewById<TextView>(R.id.pressure_in_mm_value_YW_ToD_TV)
        val pressureInhPaValueToDTV =
            view.findViewById<TextView>(R.id.pressure_in_hPa_value_YW_ToD_TV)
        val humidityInPercentsValueToDTV =
            view.findViewById<TextView>(R.id.humidity_in_percents_value_YW_ToD_TV)
        val precipitationTypeValueToDTV =
            view.findViewById<TextView>(R.id.precipitation_type_value_YW_ToD_TV)
        val precipitationStrengthValueToDTV =
            view.findViewById<TextView>(R.id.precipitation_strength_value_YW_ToD_TV)
        val precipitationInMmValueTV =
            view.findViewById<TextView>(R.id.precipitation_in_mm_value_YW_ToD_TV)
        val precipitationInMinutesValueTV =
            view.findViewById<TextView>(R.id.precipitation_in_minutes_value_YW_ToD_TV)
        val daytimeValueToDTV = view.findViewById<TextView>(R.id.daytime_value_YW_ToD_TV)
        val polarValueToDTV = view.findViewById<TextView>(R.id.polar_value_YW_ToD_TV)
        val cloudinessValueToDTV = view.findViewById<TextView>(R.id.cloudiness_value_YW_ToD_TV)
        val titleValueToDTV = view.findViewById<TextView>(R.id.title_value_YW_ToD_TV)

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
