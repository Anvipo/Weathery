package ru.mts.avpopo85.weathery.domain.weather.yandexWeather.currentWeather

import android.content.Context
import ru.mts.avpopo85.weathery.data.models.CurrentWeatherResponse
import ru.mts.avpopo85.weathery.domain.models.CurrentWeather
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.*
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getWaterTemperatureString
import ru.mts.avpopo85.weathery.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.utils.toDate
import javax.inject.Inject

class YandexCurrentWeatherMapper
@Inject constructor(private val context: Context) {

    fun mapCurrentWeatherResponse(currentWeatherResponse: CurrentWeatherResponse): CurrentWeather =
        CurrentWeather(
            cloudiness = context.getCloudinessString(currentWeatherResponse.cloudiness),
            daytime = context.getDaytimeString(currentWeatherResponse.daytime),
            feelsLikeTemperature = currentWeatherResponse.feelsLikeTemperature.roundIfNeeded(),
            humidity = currentWeatherResponse.humidity.roundIfNeeded(),
            iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${currentWeatherResponse.iconId}.svg",
            observationUnixTime = currentWeatherResponse.observationUnixTime.toDate(),
            polar = context.getPolarString(currentWeatherResponse.polar),
            precipitationStrength = context.getPrecipitationStrengthString(currentWeatherResponse.precipitationStrength),
            precipitationType = context.getPrecipitationTypeString(currentWeatherResponse.precipitationType),
            atmosphericPressureInMmHg = currentWeatherResponse.atmosphericPressureInMmHg.roundIfNeeded(),
            atmosphericPressureInhPa = currentWeatherResponse.atmosphericPressureInhPa.roundIfNeeded(),
            season = context.getSeasonString(currentWeatherResponse.season),
            temperature = currentWeatherResponse.temperature.roundIfNeeded(),
            waterTemperature = getWaterTemperatureString(currentWeatherResponse.waterTemperature),
            weatherDescription = context.getWeatherDescriptionString(currentWeatherResponse.weatherDescription),
            windDirection = context.getWindDirectionString(currentWeatherResponse.windDirection),
            windGustsSpeed = currentWeatherResponse.windGustsSpeed.roundIfNeeded(),
            windSpeed = currentWeatherResponse.windSpeed.roundIfNeeded()
        )

}


