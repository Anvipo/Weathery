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
    private val apiService: IOWMCurrentWeatherApiService,
    private val networkManager: NetworkManager,
    private val dbService: ICurrentWeatherDbService<OWMCurrentWeatherResponseType>
) : ICurrentWeatherRepository<OWMCurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<OWMCurrentWeatherResponseType> {
        val dbCall = dbService
            .getCurrentWeatherResponse(networkManager.isConnectedToInternet)

        if (!networkManager.isConnectedToInternet) {
            return dbCall
        }

        return dbCall
            .onErrorResumeNext { _ ->
                apiService
                    .getCurrentWeatherById(KRASNODAR_ID)
                    .flatMap {
                        dbService.saveCurrentWeatherResponse(it)
                    }
            }
    }

}
