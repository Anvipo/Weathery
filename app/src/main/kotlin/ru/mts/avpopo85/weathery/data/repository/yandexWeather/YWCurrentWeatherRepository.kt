package ru.mts.avpopo85.weathery.data.repository.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.utils.YW_CURRENT_WEATHER_PARAMETERS
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.YWCurrentWeatherResponseType
import javax.inject.Inject

class YWCurrentWeatherRepository
@Inject constructor(
    private val ywCurrentWeatherApiService: IYWCurrentWeatherApiService,
    private val networkManager: NetworkManager,
    private val currentWeatherDbService: ICurrentWeatherDbService<YWCurrentWeatherResponseType>
) : ICurrentWeatherRepository<YWCurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<YWCurrentWeatherResponseType> {
        if (!networkManager.isConnectedToInternet) {
            return currentWeatherDbService.getCurrentWeatherResponse(false)
        }

        val apiCall: Single<YWCurrentWeatherResponseType> =
            ywCurrentWeatherApiService
                .getCurrentWeather(
                    YW_CURRENT_WEATHER_PARAMETERS.latitude,
                    YW_CURRENT_WEATHER_PARAMETERS.longitude,
                    YW_CURRENT_WEATHER_PARAMETERS.language
                )
                .map { it.currentWeatherResponse }

        return apiCall.flatMap { currentWeatherDbService.saveCurrentWeatherResponse(it) }
    }

}
