package ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWDayShortResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWDayTimeResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWHourInfoResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWPartsResponse
import ru.mts.avpopo85.weathery.domain.mapper.base.ForecastMapper
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.*
import ru.mts.avpopo85.weathery.domain.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.utils.toDate
import ru.mts.avpopo85.weathery.utils.ForecastListResponseType
import ru.mts.avpopo85.weathery.utils.ForecastListType
import javax.inject.Inject

class YWForecastMapper
@Inject constructor(private val context: Context) :
    ForecastMapper<ForecastListResponseType, ForecastListType> {

    override fun mapForecast(forecastListResponse: ForecastListResponseType): ForecastListType =
        forecastListResponse.map {
            YWForecast(
                date = it.date,
                date_ts = it.date_ts.toDate(),
                weekSerialNumber = it.weekSerialNumber,
                sunriseInLocalTime = it.sunriseInLocalTime,
                sunsetInLocalTime = it.sunsetInLocalTime,
                moonCode = context.getMoonCodeString(it.moonCode),
                moonText = context.getMoonTextString(it.moonText),
                YWParts = mapPartsResponse(it.YWPartsResponse!!),
                YWHours = mapHoursResponse(it.YWHours)
            )
        }

    private fun mapHoursResponse(YWHourInfoResponse: List<YWHourInfoResponse>?): List<YWHourInfo>? =
        YWHourInfoResponse?.map {
            YWHourInfo(
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

    private fun mapPartsResponse(YWPartsResponse: YWPartsResponse): YWParts =
        YWPartsResponse.let {
            YWParts(
                nightForecast = mapDayTime(
                    "Прогноз на ночь",
                    it.nightForecastResponseYW!!
                ),
                morningForecast = mapDayTime(
                    "Прогноз на утро",
                    it.morningForecastResponseYW!!
                ),
                YWDayForecast = mapDayTime(
                    "Прогноз на день",
                    it.YWDayForecastResponse!!
                ),
                eveningForecast = mapDayTime(
                    "Прогноз на вечер",
                    it.eveningForecastResponseYW!!
                ),
                _12HoursYWDayForecast = map12HoursForecast(
                    "12 часовой прогноз на день",
                    it._12HoursYWDayForecastResponse!!
                ),
                _12HoursNightForecast = map12HoursForecast(
                    "12 часовой прогноз на ночь",
                    it._12HoursNightForecastResponseYW!!
                )
            )
        }

    private fun mapDayTime(
        title: String,
        YWDayForecastResponse: YWDayTimeResponse
    ): YWDayTime =
        YWDayForecastResponse.let {
            YWDayTime(
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

    private fun map12HoursForecast(
        title: String,
        _12HoursForecast: YWDayShortResponse
    ): YWDayShort =
        _12HoursForecast.let {
            YWDayShort(
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
