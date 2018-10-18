package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.yandexWeather.IYWHourInfo
import ru.mts.avpopo85.weathery.presentation.utils.CELSIUS_DEGREE

class YW12HoursForecastViewHolder(private val view: View) : ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(YWHourInfo: IYWHourInfo) {
        val hourInLocalValueTV = view.findViewById<TextView>(R.id.hour_in_local_value_YW_F_TV)
        val hourTemperatureValueTV =
            view.findViewById<TextView>(R.id.hour_temperature_value_YW_F_TV)
        val feelsLikeHourTemperatureValueTV =
            view.findViewById<TextView>(R.id.feels_like_hour_temperature_value_YW_F_TV)
        val hourConditionValueTV = view.findViewById<TextView>(R.id.hour_condition_value_YW_F_TV)
        val hourWindSpeedValueTV = view.findViewById<TextView>(R.id.hour_wind_speed_value_YW_F_TV)
        val hourWindGustValueTV = view.findViewById<TextView>(R.id.hour_wind_gust_value_YW_F_TV)
        val hourWindDirectionValueTV =
            view.findViewById<TextView>(R.id.hour_wind_direction_value_YW_F_TV)
        val hourPressureInMmHgValueTV =
            view.findViewById<TextView>(R.id.hour_pressure_in_mm_value_YW_F_TV)
        val hourPressureInhPaValueTV =
            view.findViewById<TextView>(R.id.hour_pressure_in_hPa_value_YW_F_TV)
        val hourHumidityInPercentsValueTV =
            view.findViewById<TextView>(R.id.hour_humidity_in_percents_value_YW_F_TV)
        val hourPrecipitationTypeValueTV =
            view.findViewById<TextView>(R.id.hour_precipitation_type_value_YW_F_TV)
        val hourPrecipitationStrengthValueTV =
            view.findViewById<TextView>(R.id.hour_precipitation_strength_value_YW_F_TV)
        val hourPrecipitationInMmValueTV =
            view.findViewById<TextView>(R.id.hour_precipitation_in_mm_value_YW_F_TV)
        val hourPrecipitationInMinutesValueTV =
            view.findViewById<TextView>(R.id.hour_precipitation_in_minutes_value_YW_F_TV)
        val hourCloudinessValueTV = view.findViewById<TextView>(R.id.hour_cloudiness_value_YW_F_TV)

        YWHourInfo.let {
            hourInLocalValueTV.text = it.hourInLocalTime
            hourTemperatureValueTV.text = "${it.temperature}$CELSIUS_DEGREE"
            feelsLikeHourTemperatureValueTV.text = "${it.feelsLikeTemperature}$CELSIUS_DEGREE"
            hourConditionValueTV.text = it.condition
            hourWindSpeedValueTV.text =
                    "${it.windSpeed} ${view.context.getString(R.string.meters_per_second)}"
            hourWindGustValueTV.text =
                    "${it.windGustsSpeed} ${view.context.getString(R.string.meters_per_second)}"
            hourWindDirectionValueTV.text = it.windDirection
            hourPressureInMmHgValueTV.text =
                    "${it.atmosphericPressureInMmHg} ${view.context.getString(R.string.mm_hg)}"
            hourPressureInhPaValueTV.text =
                    "${it.atmosphericPressureInhPa} ${view.context.getString(R.string.hPa)}"
            hourHumidityInPercentsValueTV.text = "${it.humidity}%"
            hourPrecipitationTypeValueTV.text = it.precipitationType
            hourPrecipitationStrengthValueTV.text = it.precipitationStrength
            hourPrecipitationInMmValueTV.text =
                    "${it.precipitationInMm} ${view.context.getString(R.string.mm)}"
            hourPrecipitationInMinutesValueTV.text =
                    "${it.precipitationInMinutes} ${view.context.getString(R.string.minutes)}"
            hourCloudinessValueTV.text = it.cloudiness
        }
    }

}
