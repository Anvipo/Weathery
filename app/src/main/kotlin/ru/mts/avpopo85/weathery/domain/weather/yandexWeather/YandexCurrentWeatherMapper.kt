package ru.mts.avpopo85.weathery.domain.weather.yandexWeather

import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getCloudnessString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getDaytimeString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getPolarString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getPrecipitationStrengthString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getPrecipitationTypeString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getSeasonString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getWaterTemperatureString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getWeatherDescriptionString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getWindDirectionString
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.CurrentWeather
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.CurrentWeatherResponse
import ru.mts.avpopo85.weathery.utils.toDate

object YandexCurrentWeatherMapper {
    fun mapCurrentWeatherResponse(currentWeatherResponse: CurrentWeatherResponse): CurrentWeather =
        CurrentWeather(
            cloudness = getCloudnessString(currentWeatherResponse.cloudness),
            daytime = getDaytimeString(currentWeatherResponse.daytime),
            feelsLikeTemperature = currentWeatherResponse.feelsLikeTemperature,
            humidityInPercents = currentWeatherResponse.humidityInPercents,
            iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${currentWeatherResponse.iconId}.svg",
            observationUnixTime = currentWeatherResponse.observationUnixTime.toDate(),
            polar = getPolarString(currentWeatherResponse.polar),
            precipitationStrength = getPrecipitationStrengthString(currentWeatherResponse.precipitationStrength),
            precipitationType = getPrecipitationTypeString(currentWeatherResponse.precipitationType),
            pressureInMmHg = currentWeatherResponse.pressureInMmHg,
            pressureInhHpa = currentWeatherResponse.pressureInhHpa,
            season = getSeasonString(currentWeatherResponse.season),
            temperature = currentWeatherResponse.temperature,
            waterTemperature = getWaterTemperatureString(currentWeatherResponse.waterTemperature),
            weatherDescription = getWeatherDescriptionString(currentWeatherResponse.weatherDescription),
            windDirection = getWindDirectionString(currentWeatherResponse.windDirection),
            windGust = currentWeatherResponse.windGust,
            windSpeed = currentWeatherResponse.windSpeed
        )

    fun getCurrentInfoList(mappedInfo: CurrentWeather): List<String> {
        val list = mutableListOf<String>()

        list.apply {
            add("Облачность: ${mappedInfo.cloudness}")
            add("Время суток: ${mappedInfo.daytime}")
            add("Температура: ${mappedInfo.temperature}°C")
            add("Ощущаемая температура: ${mappedInfo.feelsLikeTemperature}°C")
            if (mappedInfo.waterTemperature != "")
                add("Температура воды: ${mappedInfo.waterTemperature}")
            add("Влажность воздуха: ${mappedInfo.humidityInPercents}%")
            add("Код расшифровки погодного описания: ${mappedInfo.weatherDescription}")
            add("Сила осадков: ${mappedInfo.precipitationStrength}")
            add("Тип осадков: ${mappedInfo.precipitationType}")
            add("Направление ветра: ${mappedInfo.windDirection}")
            add("Скорость порывов ветра: ${mappedInfo.windGust} м/c")
            add("Скорость ветра: ${mappedInfo.windSpeed} м/с")
            add("Давление (мм рт. ст.): ${mappedInfo.pressureInMmHg}")
            add("Давление (в гектопаскалях): ${mappedInfo.pressureInhHpa}")
            add("Бывает ли полярная ночь/день: ${mappedInfo.polar}")
            add("Время года в данном населённом пункте: ${mappedInfo.season}")
            add("Код иконки погоды:\n${mappedInfo.iconUrl}")
            add("Время замера погодных данных в формате Unixtime:\n${mappedInfo.observationUnixTime}")
        }

        return list
    }
}


