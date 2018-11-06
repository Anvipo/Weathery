package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.data.db.implementation.realm.weather.openWeatherMap.utils.OWM_CURRENT_WEATHER_CACHE_LIFETIME_IN_MS
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.common.AbsWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap.utils.getWindDirectionString
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.toTime
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather.OWMSys
import ru.mts.avpopo85.weathery.utils.common.PrecipitationType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherMapper
@Inject constructor(private val context: Context) :
    AbsWeatherMapper<OWMCurrentWeatherResponseType>(),
    ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType> {

    override fun mapCurrentWeatherResponse(
        currentWeatherResponseData: OWMCurrentWeatherResponseType
    ): OWMCurrentWeatherType =
        currentWeatherResponseData.let { response ->
            val main = response.main!!

            val wind = response.wind!!

            val weather = response.weather.first()!!

            OWMCurrentWeatherType(
                weather = weather.let {
                    OWMWeather(
                        conditionCode = it.conditionCode,
                        groupOfWeatherParameters = it.groupOfWeatherParameters,
                        description = it.description,
                        icon = it.icon
                    )
                },
                temperature = main.temperature.roundIfNeeded(),
                atmosphericPressureInhPa = main.atmosphericPressureInhPa.roundIfNeeded(),
                humidity = main.humidity.roundIfNeeded(),
                visibilityInMeters = response.visibility,
                windSpeed = wind.speedInUnits.roundIfNeeded(),
                windDirection = context.getWindDirectionString(wind.directionInDegrees),
                cloudiness = response.clouds!!.cloudiness.roundIfNeeded(),
                timeOfDataCalculation = response.timeOfDataCalculationUnixUTCInSeconds.toTime(),
                sys = response.sys!!.let {
                    OWMSys(
                        sunrise = it.sunrise.toTime(),
                        sunset = it.sunset.toTime()
                    )
                },
                cityName = response.cityName,
                isFresh = response.isFresh,
                precipitationType = getPrecipitationType(weather.conditionCode),
                weatherDescription = weather.description
            )
        }

    override val cacheLifeTimeInMs: Long = OWM_CURRENT_WEATHER_CACHE_LIFETIME_IN_MS

    private fun getPrecipitationType(conditionCode: Int): PrecipitationType = when (conditionCode) {
        in 200..299 -> PrecipitationType.THUNDERSTORM
        in 300..399 -> PrecipitationType.DRIZZLE
        in 500..599 -> PrecipitationType.RAIN
        in 600..699 -> PrecipitationType.SNOW
        in 700..799 -> PrecipitationType.ATMOSPHERE
        800 -> PrecipitationType.CLEAR
        in 801..899 -> PrecipitationType.CLOUDS
        else -> PrecipitationType.UNKNOWN
    }

}
