package ru.mts.avpopo85.weathery.data.repository.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.CurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWCurrentWeatherParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWWeatherResponse
import ru.mts.avpopo85.weathery.data.network.base.CurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.network.base.NetworkManager
import ru.mts.avpopo85.weathery.data.utils.YW_CURRENT_WEATHER_PARAMETERS
import ru.mts.avpopo85.weathery.domain.repository.CurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import javax.inject.Inject

class YWCurrentWeatherRepository
@Inject constructor(
    private val currentWeatherApiService: CurrentWeatherApiService<YWWeatherResponse, YWCurrentWeatherParameters>,
    private val networkManager: NetworkManager,
    private val currentWeatherDbService: CurrentWeatherDbService<YWCurrentWeatherResponseType>
) : CurrentWeatherRepository<YWCurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<YWCurrentWeatherResponseType> {
        if (!networkManager.isConnectedToInternet) {
            return currentWeatherDbService.getCurrentWeatherResponse(false)
        }

        val apiCall: Single<YWCurrentWeatherResponseType> =
            currentWeatherApiService
                .getCurrentWeather(YW_CURRENT_WEATHER_PARAMETERS)
                .map { it.currentWeatherResponse }

        return apiCall.flatMap { currentWeatherDbService.saveCurrentWeatherResponse(it) }
    }

}
