package ru.mts.avpopo85.weathery.domain.mapper.implementation.openWeatherMap

import android.content.Context
import ru.mts.avpopo85.weathery.domain.mapper.base.ICurrentWeatherMapper
import ru.mts.avpopo85.weathery.domain.model.implementation.openWeatherMap.*
import ru.mts.avpopo85.weathery.domain.utils.toDateTime
import ru.mts.avpopo85.weathery.domain.utils.toTime
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherType
import javax.inject.Inject

class OWMCurrentWeatherMapper
@Inject constructor(private val context: Context) :
    ICurrentWeatherMapper<OWMCurrentWeatherResponseType, OWMCurrentWeatherType> {

    override fun mapCurrentWeatherResponse(currentWeatherResponse: OWMCurrentWeatherResponseType): OWMCurrentWeatherType =
        currentWeatherResponse.let {
            OWMCurrentWeatherType(
                coordinates = it.coordinates!!.let { coordinates ->
                    OWMCoordinates(
                        coordinates.longitude,
                        coordinates.latitude
                    )
                },
                weather = it.weather.map { weather ->
                    OWMWeather(
                        weather.id,
                        weather.groupOfWeatherParameters,
                        weather.description,
                        weather.icon
                    )
                },
                base = it.base,
                main = it.main!!.let { main ->
                    OWMMain(
                        main.temperature,
                        main.atmosphericPressureInhPa,
                        main.humidity,
                        main.minimumTemperature,
                        main.maximumTemperature
                    )
                },
                visibility = it.visibility,
                wind = it.wind!!.let { wind ->
                    OWMWind(
                        wind.speed,
                        wind.directionInDegrees
                    )
                },
                clouds = OWMClouds(it.clouds!!.cloudiness),
                date = it.date.toDateTime(),
                sys = it.sys!!.let { sys ->
                    OWMSys(
                        sys.type,
                        sys.id,
                        sys.message,
                        sys.countryCode,
                        sys.sunrise.toTime(),
                        sys.sunset.toTime()
                    )
                },
                cityID = it.cityID,
                cityName = it.cityName,
                code = it.code
            )
        }

}


