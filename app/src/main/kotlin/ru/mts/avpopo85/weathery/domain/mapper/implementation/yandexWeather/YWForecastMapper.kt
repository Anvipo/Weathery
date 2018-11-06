package ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather.utils.YW_DEFAULT_CACHE_LIFETIME
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast.IYWDayShortResponse
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast.IYWDayTimeResponse
import ru.mts.avpopo85.weathery.data.model.base.yandexWeather.forecast.IYWHourInfoResponse
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.forecast.YWPartsResponse
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.AbsWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.utils.getDaytimeString
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.toDate
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.utils.*
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast.YWDayShort
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast.YWDayTime
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast.YWHourInfo
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.forecast.YWParts
import ru.mts.avpopo85.weathery.utils.common.PrecipitationType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastType
import javax.inject.Inject

class YWForecastMapper
@Inject constructor(private val context: Context) :
    AbsWeatherMapper<YWForecastResponseType>(),
    IForecastMapper<YWForecastListResponseType, YWForecastListType> {

    override fun mapForecast(forecastListResponse: YWForecastListResponseType): YWForecastListType =
        forecastListResponse.map {
            YWForecastType(
                cityName = it.cityName,
                date = it.dateUTC,
                dateInSeconds = it.timeOfDataCalculationUnixUTCInSeconds,
                dateInUnixtime = it.timeOfDataCalculationUnixUTCInSeconds.toDate(),
                weekSerialNumber = it.weekSerialNumber,
                sunriseInLocalTime = it.sunriseInLocalTime,
                sunsetInLocalTime = it.sunsetInLocalTime,
                moonCode = context.getMoonCodeString(it.moonCode),
                moonText = context.getMoonTextString(it.moonText),
                parts = mapPartsResponse(it.partsResponse!!),
                hours = mapHoursResponse(it.hours),
                isFresh = it.isFresh,
                precipitationType = getPrecipitationType(it.hours.first()!!.precipitationType),
                cloudiness = it.hours.first()!!.cloudiness.roundIfNeeded(),
                weatherDescription = it.hours.first()!!.condition
            )
        }

    override val cacheLifeTimeInMs: Long = YW_DEFAULT_CACHE_LIFETIME

    private fun getPrecipitationType(conditionCode: Int): PrecipitationType = when (conditionCode) {
        //todo
        in 200..299 -> PrecipitationType.THUNDERSTORM
        in 300..399 -> PrecipitationType.DRIZZLE
        in 500..599 -> PrecipitationType.RAIN
        in 600..699 -> PrecipitationType.SNOW
        in 700..799 -> PrecipitationType.ATMOSPHERE
        else -> PrecipitationType.UNKNOWN
    }


    private fun mapHoursResponse(YWHourInfoResponse: List<IYWHourInfoResponse>?): List<YWHourInfo>? =
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
                    context.getString(R.string.night_forecast),
                    it.nightForecastResponse!!
                ),
                morningForecast = mapDayTime(
                    context.getString(R.string.morning_forecast),
                    it.morningForecastResponse!!
                ),
                dayForecast = mapDayTime(
                    context.getString(R.string.day_forecast),
                    it.dayForecastResponse!!
                ),
                eveningForecast = mapDayTime(
                    context.getString(R.string.evening_forecast),
                    it.eveningForecastResponse!!
                ),
                _12HourForecastForDay = map12HoursForecast(
                    context.getString(R.string._12_hour_forecast_for_day),
                    it._12HoursDayForecastResponse!!
                ),
                _12HourForecastForNight = map12HoursForecast(
                    context.getString(R.string._12_hour_forecast_for_night),
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
        }

}
