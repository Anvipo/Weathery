package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.utils.OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.AbsWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.utils.getDaytimeString
import ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap.utils.getWindDirectionString
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.forecast.OWMForecastMain
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.toDateTime
import ru.mts.avpopo85.weathery.utils.openWeatherMap.*
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
                cityName = forecastResponse.cityName,
                date = forecastResponse.timeOfDataCalculationUnixUTCInSeconds.toDateTime(),
                cloudiness = forecastResponse.clouds!!.cloudiness,
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
                        temperature = it.temperature,
                        humidity = it.humidity,
                        atmosphericPressureOnTheGroundLevelInhPa = it.atmosphericPressureOnTheGroundLevelInhPa,
                        atmosphericPressureOnTheSeaLevelInhPa = it.atmosphericPressureOnTheSeaLevelInhPa
                    )
                },
                isFresh = forecastResponse.isFresh
            )
        }

    override val cacheLifeTimeInMs: Long = OWM_FORECAST_DEFAULT_CACHE_LIFETIME_IN_MS

}
