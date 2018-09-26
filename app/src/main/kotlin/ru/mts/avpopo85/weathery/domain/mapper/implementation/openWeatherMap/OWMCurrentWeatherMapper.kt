package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather.OWMSys
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.common.OWMWeather
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather.OWMWind
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather.OWMCurrentWeatherMain
import ru.mts.avpopo85.weathery.domain.utils.roundIfNeeded
import ru.mts.avpopo85.weathery.domain.utils.toTime
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherMapper
@Inject constructor(val context: Context) :
    ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType> {

    override fun mapCurrentWeatherResponse(currentWeatherResponseData: OWMCurrentWeatherResponseType): OWMCurrentWeatherType =
        currentWeatherResponseData.let { currentWeatherResponse: OWMCurrentWeatherResponseType ->
            OWMCurrentWeatherType(
                weather = currentWeatherResponse.weather.map {
                    OWMWeather(
                        conditionCode = it.conditionCode,
                        groupOfWeatherParameters = it.groupOfWeatherParameters,
                        description = it.description,
                        icon = it.icon
                    )
                },
                main = currentWeatherResponse.main!!.let {
                    OWMCurrentWeatherMain(
                        temperature = it.temperature.roundIfNeeded(),
                        atmosphericPressureInhPa = it.atmosphericPressureInhPa,
                        humidity = it.humidity
                    )
                },
                visibilityInMeters = currentWeatherResponse.visibility,
                wind = currentWeatherResponse.wind!!.let {
                    OWMWind(
                        speedInUnits = it.speedInUnits.roundIfNeeded(),
                        direction = context.getWindDirectionString(it.directionInDegrees)
                    )
                },
                cloudiness = currentWeatherResponse.clouds!!.cloudiness,
                timeOfDataCalculation = currentWeatherResponse.timeOfDataCalculationUnixUTC.toTime(),
                sys = currentWeatherResponse.sys!!.let {
                    OWMSys(
                        sunrise = it.sunrise.toTime(),
                        sunset = it.sunset.toTime()
                    )
                }
            )
        }

}


