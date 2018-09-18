package ru.mts.avpopo85.weathery.data.repositories

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.models.WeatherResponse

interface WeatherRepository {

    fun getForecast(): Single<WeatherResponse>

    fun getCurrentWeather(): Single<WeatherResponse>

}
