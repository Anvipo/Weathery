package ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.yandexWeather.utils.YW_DEFAULT_CACHE_LIFETIME
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.AbsWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.utils.getDaytimeString
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.toDate
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.utils.*
import ru.mts.avpopo85.weathery.domain.mapper.implementation.yandexWeather.utils.YandexWeatherMapper.getWaterTemperatureString
import ru.mts.avpopo85.weathery.utils.common.PrecipitationType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherType
import javax.inject.Inject

class YWCurrentWeatherMapper
@Inject constructor(private val context: Context) :
    AbsWeatherMapper<YWCurrentWeatherResponseType>(),
    ICurrentWeatherMapper<YWCurrentWeatherResponseType, YWCurrentWeatherType> {

    override fun mapCurrentWeatherResponse(currentWeatherResponseData: YWCurrentWeatherResponseType): YWCurrentWeatherType =
        currentWeatherResponseData.let {
            YWCurrentWeatherType(
                //todo cloudiness
                cloudiness = context.getCloudinessString(it.cloudiness),
                daytime = context.getDaytimeString(it.daytime),
                feelsLikeTemperature = it.feelsLikeTemperature.roundIfNeeded(),
                humidity = it.humidity.roundIfNeeded(),
                iconUrl = "https://yastatic.net/weather/i/icons/blueye/color/svg/${it.iconId}.svg",
                timeOfDataCalculation = it.timeOfDataCalculationUnixUTCInSeconds.toDate(),
                polar = context.getPolarString(it.polar),
                precipitationStrength = context.getPrecipitationStrengthString(it.precipitationStrength),
                precipitationTypeString = context.getPrecipitationTypeString(it.precipitationType),
                precipitationType = getPrecipitationType(it.precipitationType),
                atmosphericPressureInMmHg = it.atmosphericPressureInMmHg.roundIfNeeded(),
                atmosphericPressureInhPa = it.atmosphericPressureInhPa.roundIfNeeded(),
                season = context.getSeasonString(it.season),
                temperature = it.temperature.roundIfNeeded(),
                waterTemperature = getWaterTemperatureString(it.waterTemperature),
                weatherDescription = context.getWeatherDescriptionString(it.weatherDescription),
                windDirection = context.getWindDirectionString(it.windDirection),
                windGustsSpeed = it.windGustsSpeed.roundIfNeeded(),
                windSpeed = it.windSpeed.roundIfNeeded(),
                cityName = it.cityName,
                isFresh = it.isFresh
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


}
