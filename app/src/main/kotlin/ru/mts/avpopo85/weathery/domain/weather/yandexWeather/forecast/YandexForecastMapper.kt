package ru.mts.avpopo85.weathery.domain.weather.yandexWeather.forecast

import android.content.Context
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.*
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.data.*
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.*
import ru.mts.avpopo85.weathery.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.utils.toDate

class YandexForecastMapper(private val context: Context) {
    fun mapForecast(forecastResponse: List<ForecastResponse>): List<Forecast> =
        forecastResponse.map {
            Forecast(
                date = it.date,
                date_ts = it.date_ts.toDate(),
                weekSerialNumber = it.weekSerialNumber,
                sunriseInLocalTime = it.sunriseInLocalTime,
                sunsetInLocalTime = it.sunsetInLocalTime,
                moonCode = context.getMoonCodeString(it.moonCode),
                moonText = context.getMoonTextString(it.moonText),
                parts = mapPartsResponse(it.partsResponse),
                hours = mapHoursResponse(it.hours)
            )
        }

    private fun mapHoursResponse(hourInfoResponse: List<HourInfoResponse>?): List<HourInfo>? =
        hourInfoResponse?.map {
            HourInfo(
                hourInLocalTime = "${it.hourInLocalTime}:00",
                hourInUnixTime = it.hourInUnixTime.toDate(),
                temperature = it.temperature.roundIfNeeded(),
                feelsLikeTemperature = it.feelsLikeTemperature.roundIfNeeded(),
                iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${it.iconId}.svg",
                condition = context.getWeatherDescriptionString(it.condition),
                windSpeed = it.windSpeed.roundIfNeeded(),
                windGustsSpeed = it.windGustsSpeed.roundIfNeeded(),
                windDirection = context.getWindDirectionString(it.windDirection),
                atmosphericPressureInMmHg = it.atmosphericPressureInMmHg.roundIfNeeded(),
                atmosphericPressureInhPa = it.atmosphericPressureInhPa.roundIfNeeded(),
                humidity = it.humidity.roundIfNeeded(),
                precipitationInMm = it.precipitationInMm.roundIfNeeded(),
                precipitationInMinutes = it.precipitationInMinutes.roundIfNeeded(),
                precipitationType = context.getPrecipitationTypeString(it.precipitationType),
                precipitationStrength = context.getPrecipitationStrengthString(it.precipitationStrength),
                cloudiness = context.getCloudinessString(it.cloudiness)
            )
        }

    private fun mapPartsResponse(partsResponse: PartsResponse): Parts =
        partsResponse.let {
            Parts(
                nightForecast = mapDayTime("Прогноз на ночь", it.nightForecastResponse),
                morningForecast = mapDayTime("Прогноз на утро", it.morningForecastResponse),
                dayForecast = mapDayTime("Прогноз на день", it.dayForecastResponse),
                eveningForecast = mapDayTime("Прогноз на вечер", it.eveningForecastResponse),
                _12HoursDayForecast = map12HoursForecast(
                    "12 часовой прогноз на день",
                    it._12HoursDayForecastResponse
                ),
                _12HoursNightForecast = map12HoursForecast(
                    "12 часовой прогноз на ночь",
                    it._12HoursNightForecastResponse
                )
            )
        }

    private fun mapDayTime(title: String, dayForecastResponse: DayTimeResponse): DayTime =
        dayForecastResponse.let {
            DayTime(
                title = title,
                temperatureMinimum = it.temperatureMinimum.roundIfNeeded(),
                temperatureMaximum = it.temperatureMaximum.roundIfNeeded(),
                temperatureAverage = it.temperatureAverage.roundIfNeeded(),
                feelsLikeTemperature = it.feelsLikeTemperature.roundIfNeeded(),
                iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${it.iconId}.svg",
                condition = context.getWeatherDescriptionString(it.condition),
                daytime = context.getDaytimeString(it.daytime),
                polar = context.getPolarString(it.polar),
                windSpeed = it.windSpeed.roundIfNeeded(),
                windGustsSpeed = it.windGustsSpeed.roundIfNeeded(),
                windDirection = context.getWindDirectionString(it.windDirection),
                atmosphericPressureInMmHg = it.atmosphericPressureInMmHg.roundIfNeeded(),
                atmosphericPressureInhPa = it.atmosphericPressureInhPa.roundIfNeeded(),
                humidity = it.humidity.roundIfNeeded(),
                precipitationInMm = it.precipitationInMm.roundIfNeeded(),
                precipitationInMinutes = it.precipitationInMinutes.roundIfNeeded(),
                precipitationType = context.getPrecipitationTypeString(it.precipitationType),
                precipitationStrength = context.getPrecipitationStrengthString(it.precipitationStrength),
                cloudiness = context.getCloudinessString(it.cloudiness)
            )
        }

    private fun map12HoursForecast(title: String, _12HoursForecast: DayShortResponse): DayShort =
        _12HoursForecast.let {
            DayShort(
                title = title,
                temperature = it.temperature.roundIfNeeded(),
                feelsLikeTemperature = it.feelsLikeTemperature.roundIfNeeded(),
                iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${it.iconId}.svg",
                condition = context.getWeatherDescriptionString(it.condition),
                windSpeed = it.windSpeed.roundIfNeeded(),
                windGustsSpeed = it.windGustsSpeed.roundIfNeeded(),
                windDirection = context.getWindDirectionString(it.windDirection),
                atmosphericPressureInMmHg = it.atmosphericPressureInMmHg.roundIfNeeded(),
                atmosphericPressureInhPa = it.atmosphericPressureInhPa.roundIfNeeded(),
                humidity = _12HoursForecast.humidity.roundIfNeeded(),
                precipitationType = context.getPrecipitationTypeString(it.precipitationType),
                precipitationStrength = context.getPrecipitationStrengthString(it.precipitationStrength),
                cloudiness = context.getCloudinessString(it.cloudiness)
            )
        }
}
