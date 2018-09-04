package ru.mts.avpopo85.weathery.domain.weather.yandexWeather

import ru.mts.avpopo85.weathery.models.weather.yandexWeather.CurrentWeather
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.CurrentWeatherResponse
import ru.mts.avpopo85.weathery.utils.MyParsingException.*
import java.util.*

object YandexWeatherInteractorHelper {
    fun mapCurrentWeather(currentWeatherResponse: CurrentWeatherResponse): CurrentWeather =
            CurrentWeather(
                    cloudness = getCloudnessString(currentWeatherResponse.cloudness),
                    daytime = getDaytimeString(currentWeatherResponse.daytime),
                    feelsLikeTemperature = currentWeatherResponse.feelsLikeTemperature,
                    humidityInPercents = currentWeatherResponse.humidityInPercents,
                    iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${currentWeatherResponse.iconId}.svg",
                    observationUnixTime = getObservationUnixTimeString(currentWeatherResponse.observationUnixTime),
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

    fun getInfoList(mappedInfo: CurrentWeather): List<String> {
        val sb = mutableListOf<String>()

        with(sb) {
            add("Облачность: ${mappedInfo.cloudness}")
            add("Время суток: ${mappedInfo.daytime}")
            add("Ощущаемая температура: ${mappedInfo.feelsLikeTemperature}°C")
            add("Влажность воздуха: ${mappedInfo.humidityInPercents}%")
            add("Код иконки погоды:\n${mappedInfo.iconUrl}")
            add("Время замера погодных данных в формате Unixtime:\n${mappedInfo.observationUnixTime}")
            add("Бывает ли полярная ночь/день: ${mappedInfo.polar}")
            add("Сила осадков: ${mappedInfo.precipitationStrength}")
            add("Тип осадков: ${mappedInfo.precipitationType}")
            add("Давление (мм рт. ст.): ${mappedInfo.pressureInMmHg}")
            add("Давление (в гектопаскалях): ${mappedInfo.pressureInhHpa}")
            add("Время года в данном населённом пункте: ${mappedInfo.season}")
            add("Температура: ${mappedInfo.temperature}°C")
            add("Температура воды: ${mappedInfo.waterTemperature}°C")
            add("Код расшифровки погодного описания: ${mappedInfo.weatherDescription}")
            add("Направление ветра: ${mappedInfo.windDirection}")
            add("Скорость порывов ветра: ${mappedInfo.windGust} м/c")
            add("Скорость ветра: ${mappedInfo.windSpeed} м/с")
        }

        return sb
    }

    fun getCloudnessString(cloudness: Double): String = when (cloudness) {
        0.0 -> "ясно"
        0.25 -> "малооблачно"
        0.5, 0.75 -> "облачно с прояснениями"
        1.0 -> "пасмурно"
        else -> throw CloudnessParsingException("Неизвестное значение параметра: $cloudness")
    }

    fun getDaytimeString(daytime: String): String = when (daytime) {
        "d" -> "светлое время суток"
        "n" -> "тёмное время суток"
        else -> throw DaytimeParsingException("Неизвестное значение параметра: $daytime")
    }

    fun getObservationUnixTimeString(observationUnixTime: Int): String {
        val time = Date(observationUnixTime * 1000L)

        return time.toString()
    }

    fun getPolarString(polar: Boolean): String = if (polar) "да" else "нет"

    fun getWaterTemperatureString(waterTemperature: Int): String =
            if (waterTemperature == 0) "Температура воды: 0°C"
            else "$waterTemperature°C"

    fun getPrecipitationStrengthString(precStrength: Double): String = when (precStrength) {
        0.0 -> "без осадков"
        0.25 -> "слабые осадки"
        0.5 -> "средние осадки"
        0.75 -> "сильные осадки"
        1.0 -> "очень сильные осадки"
        else -> throw PrecipitationStrengthParsingException("Неизвестное значение параметра: $precStrength")
    }

    fun getPrecipitationTypeString(precType: Int): String = when (precType) {
        0 -> "без осадков"
        1 -> "дождь"
        2 -> "дождь со снегом"
        3 -> "снег"
        else -> throw PrecipitationTypeParsingException("Неизвестное значение параметра: $precType")
    }

    fun getSeasonString(season: String): String = when (season) {
        "summer" -> "лето"
        "autumn" -> "осень"
        "winter" -> "зима"
        "spring" -> "весна"
        else -> throw SeasonParsingException("Неизвестное значение параметра: $season")
    }

    fun getWeatherDescriptionString(season: String): String = when (season) {
        "clear" -> "ясно"
        "partly-cloudy" -> "малооблачно"
        "cloudy" -> "облачно с прояснениями"
        "overcast" -> "пасмурно"
        "partly-cloudy-and-light-rain", "cloudy-and-light-rain", "overcast-and-light-rain" -> "небольшой дождь"
        "partly-cloudy-and-rain", "cloudy-and-rain" -> "дождь"
        "overcast-and-rain" -> "сильный дождь"
        "overcast-thunderstorms-with-rain" -> "сильный дождь, гроза"
        "overcast-and-wet-snow" -> "дождь со снегом"
        "partly-cloudy-and-light-snow", "cloudy-and-light-snow", "overcast-and-light-snow" -> "небольшой снег"
        "partly-cloudy-and-snow", "cloudy-and-snow" -> "снег"
        "overcast-and-snow" -> "снегопад"
        else -> throw WeatherDescriptionParsingException("Неизвестное значение параметра: $season")
    }

    fun getWindDirectionString(windDirection: String): String = when (windDirection) {
        "nw" -> "северо-западное"
        "n" -> "северное"
        "ne" -> "северо-восточное"
        "e" -> "восточное"
        "se" -> "юго-восточное"
        "s" -> "южное"
        "sw" -> "юго-западное"
        "w" -> "западное"
        "c" -> "штиль"
        else -> throw WindDirectionParsingException("Неизвестное значение параметра: $windDirection")
    }


}


