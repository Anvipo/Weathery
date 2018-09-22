package ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.domain.mapper.base.CurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.YandexWeatherMapper.getWaterTemperatureString
import ru.mts.avpopo85.weathery.domain.model.implementation.yandexWeather.YWCurrentWeather
import ru.mts.avpopo85.weathery.domain.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.utils.toDate
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherMapper
@Inject constructor(private val context: Context) :
    CurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType> {

    override fun mapCurrentWeatherResponse(currentWeatherResponse: YWCurrentWeatherResponseType): YWCurrentWeatherType =
        currentWeatherResponse.let {
            YWCurrentWeather(
                cloudiness = context.getCloudinessString(it.cloudiness),
                daytime = context.getDaytimeString(it.daytime),
                feelsLikeTemperature = it.feelsLikeTemperature.roundIfNeeded(),
                humidity = it.humidity.roundIfNeeded(),
                iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${it.iconId}.svg",
                observationUnixTime = it.observationUnixTime.toDate(),
                polar = context.getPolarString(it.polar),
                precipitationStrength = context.getPrecipitationStrengthString(it.precipitationStrength),
                precipitationType = context.getPrecipitationTypeString(it.precipitationType),
                atmosphericPressureInMmHg = it.atmosphericPressureInMmHg.roundIfNeeded(),
                atmosphericPressureInhPa = it.atmosphericPressureInhPa.roundIfNeeded(),
                season = context.getSeasonString(it.season),
                temperature = it.temperature.roundIfNeeded(),
                waterTemperature = getWaterTemperatureString(it.waterTemperature),
                weatherDescription = context.getWeatherDescriptionString(it.weatherDescription),
                windDirection = context.getWindDirectionString(it.windDirection),
                windGustsSpeed = it.windGustsSpeed.roundIfNeeded(),
                windSpeed = it.windSpeed.roundIfNeeded()
            )
        }

}


