package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.getDaytimeString
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.forecast.OWMForecastMain
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather.OWMWind
import ru.mts.avpopo85.weathery.domain.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.utils.toDateTime
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType
import javax.inject.Inject

class OWMForecastMapper
@Inject constructor(private val context: Context) :
    IForecastMapper<OWMForecastListResponseType, OWMForecastListType> {

    override fun mapForecast(forecastListResponse: OWMForecastListResponseType): OWMForecastListType =
        forecastListResponse.map { forecastResponse: OWMForecastResponseType ->
            OWMForecastType(
                date = forecastResponse.dateInUnixtimeUTC.toDateTime(),
                cloudiness = forecastResponse.clouds!!.cloudiness,
                wind = forecastResponse.wind!!.let {
                    OWMWind(
                        speedInUnits = it.speedInUnits.roundIfNeeded(),
                        direction = context.getWindDirectionString(it.directionInDegrees)
                    )
                },
                weather = forecastResponse.weather.first()!!.let {
                    OWMWeather(
                        conditionCode = it.conditionCode,
                        groupOfWeatherParameters = it.groupOfWeatherParameters,
                        description = it.description,
                        icon = it.icon
                    )
                },
                dayTime = context.getDaytimeString(forecastResponse.sys!!.dayTime),
                rainVolumeMm = forecastResponse.rain?.rainVolumeForLast3hoursMm ?: 0.0,
                main = forecastResponse.main!!.let {
                    OWMForecastMain(
                        temperature = it.temperature,
                        humidity = it.humidity,
                        atmosphericPressureOnTheGroundLevelInhPa = it.atmosphericPressureOnTheGroundLevelInhPa,
                        atmosphericPressureOnTheSeaLevelInhPa = it.atmosphericPressureOnTheSeaLevelInhPa
                    )
                }
            )
        }

    /*private fun mapHoursResponse(YWHourInfoResponse: List<IYWHourInfoResponse>?): List<YWHourInfo>? =
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
                    it.nightForecastResponse!!
                ),
                morningForecast = mapDayTime(
                    "Прогноз на утро",
                    it.morningForecastResponse!!
                ),
                dayForecast = mapDayTime(
                    "Прогноз на день",
                    it.dayForecastResponse!!
                ),
                eveningForecast = mapDayTime(
                    "Прогноз на вечер",
                    it.eveningForecastResponse!!
                ),
                _12HoursDayForecast = map12HoursForecast(
                    "12 часовой прогноз на день",
                    it._12HoursDayForecastResponse!!
                ),
                _12HoursNightForecast = map12HoursForecast(
                    "12 часовой прогноз на ночь",
                    it._12HoursNightForecastResponse!!
                )
            )
        }

    private fun mapDayTime(
        title: String,
        YWDayForecastResponse: IYWDayTimeResponse
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
        _12HoursForecast: IYWDayShortResponse
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
        }*/

}
