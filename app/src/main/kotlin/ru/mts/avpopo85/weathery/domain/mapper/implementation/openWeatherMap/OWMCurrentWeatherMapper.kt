package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.mapper.implementation.utils.toTime
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather.OWMSys
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherMapper
@Inject constructor(private val context: Context) :
    ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType> {

    override fun mapCurrentWeatherResponse(
        currentWeatherResponseData: OWMCurrentWeatherResponseType
    ): OWMCurrentWeatherType =
        currentWeatherResponseData.let { response ->
            val main = response.main!!

            val wind = response.wind!!

            OWMCurrentWeatherType(
                weather = response.weather.first()!!.let {
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
                cloudiness = response.clouds!!.cloudiness.toString(),
                timeOfDataCalculation = response.timeOfDataCalculationUnixUTCInSeconds.toTime(),
                sys = response.sys!!.let {
                    OWMSys(
                        sunrise = it.sunrise.toTime(),
                        sunset = it.sunset.toTime()
                    )
                },
                cityName = response.cityName
            )
        }

}
