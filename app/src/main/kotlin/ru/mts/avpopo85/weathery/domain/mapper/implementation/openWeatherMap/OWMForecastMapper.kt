package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.utils.OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.AbsWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.utils.getDaytimeString
import ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap.utils.getWindDirectionString
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.toFullDateTime
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.forecast.OWMForecastMain
import ru.mts.avpopo85.weathery.utils.common.PrecipitationType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMListItemResponseType
import javax.inject.Inject

class OWMForecastMapper
@Inject constructor(private val context: Context) :
    AbsWeatherMapper<OWMListItemResponseType>(),
    IForecastMapper<OWMForecastListResponseType, OWMForecastListType> {

    override fun mapForecast(forecastListResponse: OWMForecastListResponseType): OWMForecastListType =
        forecastListResponse.map { forecastResponse: OWMListItemResponseType ->
            val wind = forecastResponse.wind!!

            val weather = forecastResponse.weather.first()!!

            OWMForecastType(
                precipitationType = getPrecipitationType(weather.conditionCode),
                cityName = forecastResponse.cityName,
                dateInSeconds = forecastResponse.timeOfDataCalculationUnixUTCInSeconds,
                date = forecastResponse.timeOfDataCalculationUnixUTCInSeconds.toFullDateTime(),
                cloudiness = forecastResponse.clouds!!.cloudiness.roundIfNeeded(),
                windDirection = context.getWindDirectionString(wind.directionInDegrees),
                windSpeed = wind.speedInUnits.roundIfNeeded(),
                weather = weather.let {
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
                        temperature = it.temperature.roundIfNeeded(),
                        humidity = it.humidity,
                        atmosphericPressureOnTheGroundLevelInhPa = it.atmosphericPressureOnTheGroundLevelInhPa,
                        atmosphericPressureOnTheSeaLevelInhPa = it.atmosphericPressureOnTheSeaLevelInhPa
                    )
                },
                isFresh = forecastResponse.isFresh,
                weatherDescription = weather.description
            )
        }

    override val cacheLifeTimeInMs: Long = OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS

    private fun getPrecipitationType(conditionCode: Int): PrecipitationType = when (conditionCode) {
        in 200..299 -> PrecipitationType.THUNDERSTORM
        in 300..399 -> PrecipitationType.DRIZZLE
        in 500..599 -> PrecipitationType.RAIN
        in 600..699 -> PrecipitationType.SNOW
        in 700..799 -> PrecipitationType.ATMOSPHERE
        800 -> PrecipitationType.CLEAR
        in 801..804 -> PrecipitationType.CLOUDS
        else -> PrecipitationType.UNKNOWN
    }

}
