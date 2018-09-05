package ru.mts.avpopo85.weathery.domain.weather.yandexWeather

import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getCloudnessString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getDaytimeString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getMoonCodeString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getMoonTextString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getPrecipitationStrengthString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getPrecipitationTypeString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getWeatherDescriptionString
import ru.mts.avpopo85.weathery.domain.weather.yandexWeather.YandexWeatherMapper.getWindDirectionString
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.*
import ru.mts.avpopo85.weathery.utils.toDate

object YandexForecastMapper {
    fun mapForecast(forecastResponse: List<ForecastResponse>): List<Forecast> {
        return forecastResponse.map {
            Forecast(
                date = it.date,
                date_ts = it.date_ts.toDate(),
                weekSerialNumber = it.weekSerialNumber,
                sunriseInLocalTime = it.sunriseInLocalTime,
                sunsetInLocalTime = it.sunsetInLocalTime,
                moonCode = getMoonCodeString(it.moonCode),
                moonText = getMoonTextString(it.moonText),
                parts = mapPartsResponse(it.partsResponse),
                hours = mapHoursResponse(it.hours)
            )
        }
    }

    private fun mapHoursResponse(hoursResponse: List<HoursResponse>): List<Hours> {
        return hoursResponse.map {
            Hours(
                hourInLocalTime = it.hourInLocalTime,
                hourInUnixTime = it.hourInUnixTime.toDate(),
                temperature = it.temperature,
                feelsLikeTemperature = it.feelsLikeTemperature,
                iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${it.iconId}.svg",
                condition = getWeatherDescriptionString(it.condition),
                windSpeed = it.windSpeed,
                windGust = it.windGust,
                windDirection = getWindDirectionString(it.windDirection),
                pressureInMM = it.pressureInMM,
                pressureInPa = it.pressureInPa,
                humidityInPercents = it.humidityInPercents,
                precipitationInMm = it.precipitationInMm,
                precipitationInMinutes = it.precipitationInMinutes,
                precipitationType = getPrecipitationTypeString(it.precipitationType),
                precipitationStrength = getPrecipitationStrengthString(it.precipitationStrength),
                cloudness = getCloudnessString(it.cloudness)
            )
        }
    }

    private fun mapPartsResponse(partsResponse: PartsResponse): Parts {
        return partsResponse.let {
            Parts(
                nightForecast = mapDayTime(it.nightForecast),
                morningForecast = mapDayTime(it.morningForecast),
                dayForecast = mapDayTime(it.dayForecastResponse),
                eveningForecast = mapDayTime(it.eveningForecast),
                _12HoursDayForecast = map12HoursForecast(it._12HoursDayForecastResponse),
                _12HoursNightForecast = map12HoursForecast(it._12HoursNightForecast)
            )
        }
    }

    private fun mapDayTime(dayForecastResponse: DayTimeResponse): DayTime {
        return dayForecastResponse.let {
            DayTime(
                temperatureMinimum = it.temperatureMinimum,
                temperatureMaximum = it.temperatureMaximum,
                temperatureAverage = it.temperatureAverage,
                feelsLikeTemperature = it.feelsLikeTemperature,
                iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${it.iconId}.svg",
                condition = getWeatherDescriptionString(it.condition),
                daytime = getDaytimeString(it.daytime),
                polar = YandexWeatherMapper.getPolarString(it.polar),
                windSpeed = it.windSpeed,
                windGust = it.windGust,
                windDirection = getWindDirectionString(it.windDirection),
                pressureInMM = it.pressureInMM,
                pressureInPa = it.pressureInPa,
                humidityInPercents = it.humidityInPercents,
                precipitationInMm = it.precipitationInMm,
                precipitationInMinutes = it.precipitationInMinutes,
                precipitationType = getPrecipitationTypeString(it.precipitationType),
                precipitationStrength = getPrecipitationStrengthString(it.precipitationStrength),
                cloudness = getCloudnessString(it.cloudness)
            )
        }
    }

    private fun map12HoursForecast(_12HoursForecast: DayShortResponse): DayShort {
        return _12HoursForecast.let {
            DayShort(
                temperature = it.temperature,
                feelsLikeTemperature = it.feelsLikeTemperature,
                iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${it.iconId}.svg",
                condition = getWeatherDescriptionString(it.condition),
                windSpeed = it.windSpeed,
                windGust = it.windGust,
                windDirection = getWindDirectionString(it.windDirection),
                pressureInMM = it.pressureInMM,
                pressureInPa = it.pressureInPa,
                humidityInPercents = _12HoursForecast.humidityInPercents,
                precipitationType = getPrecipitationTypeString(it.precipitationType),
                precipitationStrength = getPrecipitationStrengthString(it.precipitationStrength),
                cloudness = getCloudnessString(it.cloudness)
            )
        }
    }

    fun getForecastInfoList(mappedInfoList: List<Forecast>): List<String> {
        return mappedInfoList.map {
            val dailyForecastString = getPartsString(it.parts).joinToString("\n")
            listOf(
                "Дата прогноза (в формате ГГГГ-ММ-ДД): ${it.date}",
                "Дата прогноза: ${it.date_ts}",
                "Фаза Луны: ${it.moonCode}",
                "Время восхода Солнца: ${it.sunriseInLocalTime}",
                "Время заката Солнца: ${it.sunsetInLocalTime}",
                "Порядковый номер недели: ${it.weekSerialNumber}",
                "\n\nПрогнозы по времени суток и 12-часовые прогнозы:\n$dailyForecastString",
                "\n\nПочасовой прогноз погоды:\n\n${getHoursString(it.hours).joinToString("\n")}"
            ).joinToString("\n", postfix = "\n\n\n\n")
        }
    }

    private fun getHoursString(hours: List<Hours>): List<String> {
        return hours.map {
            "\n\t" + listOf(
//                "Прогноз на: ${it.hourInLocalTime}",
                "Прогноз на: ${it.hourInUnixTime}",
                "Облачность: ${it.cloudness}",
                "Погода: ${it.condition}",
                "Давление (в мм рт. ст.): ${it.pressureInMM}",
                "Давление (в гектопаскалях): ${it.pressureInPa}",
                "Температура: ${it.temperature}°C",
                "Ощущаемая температура: ${it.feelsLikeTemperature}°C",
                "Влажность воздуха: ${it.humidityInPercents}%",
                "Сила осадков: ${it.precipitationStrength}",
                "Тип осадков: ${it.precipitationType}",
                "Прогнозируемый период осадков (в минутах): ${it.precipitationInMinutes}",
                "Прогнозируемое количество осадков (в мм): ${it.precipitationInMm}",
                "Направление ветра: ${it.windDirection}",
                "Скорость порывов ветра: ${it.windGust} м/c",
                "Скорость ветра: ${it.windSpeed} м/с"
//                "Код иконки погоды:\n${it.iconUrl}"
            ).joinToString("\n\t\t")
        }
    }

    @Suppress("LocalVariableName")
    private fun getPartsString(parts: Parts): List<String> {
        return parts.let {
            val separator = "\n\t\t"

            val _12HoursDayStr =
                separator + getDayShortString(it._12HoursDayForecast).joinToString(separator)

            val _12HoursNightString =
                separator + getDayShortString(it._12HoursNightForecast).joinToString(separator)

            val morningForecast =
                separator + getDayTimeString(it.morningForecast).joinToString(separator)

            val dayForecast =
                separator + getDayTimeString(it.dayForecast).joinToString(separator)

            val eveningForecast =
                separator + getDayTimeString(it.eveningForecast).joinToString(separator)

            val nightForecast =
                separator + getDayTimeString(it.nightForecast).joinToString(separator)

            listOf(
                "\n\t12-часовой прогноз на день текущих суток:$_12HoursDayStr",
                "\n\t12-часовой прогноз на ночь текущих суток:$_12HoursNightString",

                "\n\tПрогноз на утро:$morningForecast",
                "\n\tПрогноз на день:$dayForecast",
                "\n\tПрогноз на вечер:$eveningForecast",
                "\n\tПрогноз на ночь:$nightForecast"
            )
        }
    }

    private fun getDayTimeString(dayTimeForecast: DayTime): List<String> {
        return dayTimeForecast.let {
            listOf(
                "Облачность: ${it.cloudness}",
                "Погода: ${it.condition}",
                "Время суток: ${it.daytime}",
                "Температура: ${it.temperatureMinimum}°C",
                "Температура: ${it.temperatureAverage}°C",
                "Температура: ${it.temperatureMaximum}°C",
                "Ощущаемая температура: ${it.feelsLikeTemperature}°C",
                "Влажность воздуха: ${it.humidityInPercents}%",
                "Сила осадков: ${it.precipitationStrength}",
                "Тип осадков: ${it.precipitationType}",
                "Прогнозируемый период осадков (в минутах): ${it.precipitationInMinutes}",
                "Прогнозируемое количество осадков (в мм): ${it.precipitationInMm}",
                "Направление ветра: ${it.windDirection}",
                "Скорость порывов ветра: ${it.windGust} м/c",
                "Скорость ветра: ${it.windSpeed} м/с",
                "Давление (мм рт. ст.): ${it.precipitationInMm}",
                "Давление (в гектопаскалях): ${it.pressureInPa}",
                "Бывает ли полярная ночь/день: ${it.polar}"
//                "Код иконки погоды:\n${it.iconUrl}"
            )
        }
    }

    private fun getDayShortString(_12HoursForecast: DayShort): List<String> {
        return _12HoursForecast.let {
            listOf(
                "Облачность: ${it.cloudness}",
                "Погода: ${it.condition}",
                "Давление (в мм рт. ст.): ${it.pressureInMM}",
                "Давление (в гектопаскалях): ${it.pressureInPa}",
                "Температура: ${it.temperature}°C",
                "Ощущаемая температура: ${it.feelsLikeTemperature}°C",
                "Влажность воздуха: ${it.humidityInPercents}%",
                "Сила осадков: ${it.precipitationStrength}",
                "Тип осадков: ${it.precipitationType}",
                "Направление ветра: ${it.windDirection}",
                "Скорость порывов ветра: ${it.windGust} м/c",
                "Скорость ветра: ${it.windSpeed} м/с"
//                "Код иконки погоды: ${it.iconUrl}"
            )
        }
    }
}
