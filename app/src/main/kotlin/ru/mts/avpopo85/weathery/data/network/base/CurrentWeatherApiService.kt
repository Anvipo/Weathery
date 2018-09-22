package ru.mts.avpopo85.weathery.data.network.base

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.model.base.WeatherResponse

interface CurrentWeatherApiService<WR : WeatherResponse, CurrentWeatherParameters> {

    fun getCurrentWeather(parameters: CurrentWeatherParameters): Single<WR>

}
