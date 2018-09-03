package ru.mts.avpopo85.weathery.data.repositories

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.network.YandexWeatherApiService
import ru.mts.avpopo85.weathery.domain.global.repositories.YandexWeatherRepository
import ru.mts.avpopo85.weathery.models.weather.yandex.CurrentWeather
import ru.mts.avpopo85.weathery.models.weather.yandex.Forecasts
import ru.mts.avpopo85.weathery.models.weather.yandex.WeatherResponse

class YandexWeatherRepositoryImpl(private val yandexWeatherApiService: YandexWeatherApiService) : YandexWeatherRepository {
    override fun getWeatherResponse2(): Observable<WeatherResponse> = yandexWeatherApiService.forecast2()
    override fun getWeatherResponse3(): Maybe<WeatherResponse> = yandexWeatherApiService.forecast3()
    override fun getWeatherResponse4(): Completable = yandexWeatherApiService.forecast4()

    override fun getWeatherResponse(): Single<WeatherResponse> = yandexWeatherApiService.forecast()


    override fun getCurrentWeather(): Single<CurrentWeather>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWeatherForecast(): Single<List<Forecasts>>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
