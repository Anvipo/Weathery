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
    private val owmForecastApiService: IOWMForecastApiService,
    private val networkManager: NetworkManager,
    private val forecastDbService: IForecastDbService<OWMForecastListResponseType>
) : IForecastRepository<OWMForecastListResponseType> {

    override fun getForecast(): Single<OWMForecastListResponseType> {
        if (!networkManager.isConnectedToInternet) {
            return forecastDbService.getForecastResponse(networkManager.isConnectedToInternet)
        }

        //TODO как выполнить запрос на сервер, если в БД ничего нет?
        return Single.ambArray(
            forecastDbService.getForecastResponse(networkManager.isConnectedToInternet),
            owmForecastApiService
                .getForecastById(OWMConstants.KRASNODAR_ID)
                .map { it.forecastsList }
                .flatMap { forecastDbService.saveForecastResponse(it) }
        )
    }

}
