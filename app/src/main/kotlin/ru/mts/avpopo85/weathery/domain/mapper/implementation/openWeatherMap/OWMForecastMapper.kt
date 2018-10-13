package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.domain.mapper.base.IForecastMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.getDaytimeString
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.forecast.OWMForecastMain
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather.OWMWind
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.toDateTime
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
                date = forecastResponse.dateInUnixUTCInSeconds.toDateTime(),
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

}
