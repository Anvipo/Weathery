package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.*
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.currentWeather.OWMCurrentWeatherMain
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
                coordinates = currentWeatherResponse.coordinates!!.let {
                    OWMCoordinates(
                        longitude = it.longitude,
                        latitude = it.latitude
                    )
                },
                weather = currentWeatherResponse.weather.map {
                    OWMWeather(
                        conditionCode = it.conditionCode,
                        groupOfWeatherParameters = it.groupOfWeatherParameters,
                        description = it.description,
                        icon = it.icon
                    )
                },
                base = currentWeatherResponse.base,
                main = currentWeatherResponse.main!!.let {
                    OWMCurrentWeatherMain(
                        temperature = it.temperature,
                        atmosphericPressureInhPa = it.atmosphericPressureInhPa,
                        humidity = it.humidity,
                        minimumTemperature = it.minimumTemperature,
                        maximumTemperature = it.maximumTemperature
                    )
                },
                visibilityInMeters = currentWeatherResponse.visibility,
                wind = currentWeatherResponse.wind!!.let {
                    OWMWind(
                        speedInUnits = it.speedInUnits,
                        direction = context.getWindDirectionString(it.directionInDegrees)
                    )
                },
                clouds = OWMClouds(currentWeatherResponse.clouds!!.cloudiness),
                timeOfDataCalculation = currentWeatherResponse.timeOfDataCalculationUnixUTC.toTime(),
                sys = currentWeatherResponse.sys!!.let {
                    OWMSys(
                        type = it.type,
                        id = it.id,
                        message = it.message,
                        countryCode = it.countryCode,
                        sunrise = it.sunrise.toTime(),
                        sunset = it.sunset.toTime()
                    )
                },
                cityID = currentWeatherResponse.cityID,
                cityName = currentWeatherResponse.cityName,
                code = currentWeatherResponse.code
            )
        }

}


