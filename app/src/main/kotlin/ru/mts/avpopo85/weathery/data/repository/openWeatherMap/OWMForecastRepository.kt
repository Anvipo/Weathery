package ru.mts.avpopo85.weathery.data.repository.openWeatherMap

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMForecastApiService
import ru.mts.avpopo85.weathery.data.utils.openWeatherMap.OWMConstants
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import javax.inject.Inject


class OWMForecastRepository
@Inject constructor(
    private val apiService: IOWMForecastApiService,
    private val networkManager: NetworkManager,
    private val dbService: IForecastDbService<OWMForecastListResponseType>
) : IForecastRepository<OWMForecastListResponseType> {

    override fun getForecast(): Single<OWMForecastListResponseType> {
        val dbCall = dbService
            .getForecastResponse(networkManager.isConnectedToInternet)

        if (!networkManager.isConnectedToInternet) {
            return dbCall
        }

        return dbCall
            .onErrorResumeNext { _ ->
                apiService
                    .getForecastById(OWMConstants.KRASNODAR_ID)
                    .map { it.forecastsList }
                    .flatMap {
                        dbService.saveForecastResponse(it)
                    }
            }
    }

}
