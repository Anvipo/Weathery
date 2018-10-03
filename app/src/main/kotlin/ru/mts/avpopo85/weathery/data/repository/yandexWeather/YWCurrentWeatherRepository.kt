package ru.mts.avpopo85.weathery.data.repository.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.utils.yandexWeather.YWConstants.YW_CURRENT_WEATHER_PARAMETERS
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import javax.inject.Inject

class YWCurrentWeatherRepository
@Inject constructor(
    private val apiService: IYWCurrentWeatherApiService,
    private val networkManager: NetworkManager,
    private val dbService: ICurrentWeatherDbService<YWCurrentWeatherResponseType>
) : ICurrentWeatherRepository<YWCurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<YWCurrentWeatherResponseType> {
        val dbCall = dbService
            .getCurrentWeatherResponse(networkManager.isConnectedToInternet)

        if (!networkManager.isConnectedToInternet) {
            return dbCall
        }

        return dbCall
            .onErrorResumeNext { _ ->
                apiService
                    .getCurrentWeather(
                        YW_CURRENT_WEATHER_PARAMETERS.latitude,
                        YW_CURRENT_WEATHER_PARAMETERS.longitude,
                        YW_CURRENT_WEATHER_PARAMETERS.language
                    )
                    .map { it.currentWeatherResponse }
                    .flatMap { currentWeatherResponse ->
                        dbService.saveCurrentWeatherResponse(currentWeatherResponse)
                    }
            }

    }

}
