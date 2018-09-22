package ru.mts.avpopo85.weathery.data.repository.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.CurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.network.base.NetworkManager
import ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather.YWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.utils.YW_CURRENT_WEATHER_PARAMETERS
import ru.mts.avpopo85.weathery.domain.repository.CurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.CurrentWeatherResponseType
import javax.inject.Inject

class YWCurrentWeatherRepository
@Inject constructor(
    private val currentWeatherApiService: YWCurrentWeatherApiService,
    private val networkManager: NetworkManager,
    private val currentWeatherDbService: CurrentWeatherDbService<CurrentWeatherResponseType>
) : CurrentWeatherRepository<CurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<CurrentWeatherResponseType> {
        if (!networkManager.isConnectedToInternet) {
            return currentWeatherDbService.getCurrentWeatherResponse(false)
        }

        val apiCall: Single<CurrentWeatherResponseType> =
            currentWeatherApiService
                .getCurrentWeather(
                    YW_CURRENT_WEATHER_PARAMETERS.latitude,
                    YW_CURRENT_WEATHER_PARAMETERS.longitude,
                    YW_CURRENT_WEATHER_PARAMETERS.language
                )
                .map { it.currentWeatherResponse }

        return apiCall.flatMap { currentWeatherDbService.saveCurrentWeatherResponse(it) }
    }

}
