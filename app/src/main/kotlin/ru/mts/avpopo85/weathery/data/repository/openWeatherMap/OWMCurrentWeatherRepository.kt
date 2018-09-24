package ru.mts.avpopo85.weathery.data.repository.openWeatherMap

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OWMConstants.KRASNODAR_ID
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMCurrentWeatherResponseType
import javax.inject.Inject

class OWMCurrentWeatherRepository
@Inject constructor(
    private val owmCurrentWeatherApiService: IOWMCurrentWeatherApiService,
    private val networkManager: NetworkManager,
    private val currentWeatherDbService: ICurrentWeatherDbService<OWMCurrentWeatherResponseType>
) : ICurrentWeatherRepository<OWMCurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<OWMCurrentWeatherResponseType> {
        if (!networkManager.isConnectedToInternet) {
            return currentWeatherDbService.getCurrentWeatherResponse(networkManager.isConnectedToInternet)
        }

        //TODO как выполнить запрос на сервер, если в БД ничего нет?
        return Single.ambArray(
            currentWeatherDbService.getCurrentWeatherResponse(networkManager.isConnectedToInternet),
            owmCurrentWeatherApiService
                .getCurrentWeatherById(KRASNODAR_ID)
                .flatMap { currentWeatherDbService.saveCurrentWeatherResponse(it) }
        )
    }

}
