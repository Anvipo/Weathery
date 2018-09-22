package ru.mts.avpopo85.weathery.data.repository.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ForecastDbService
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWForecastParameters
import ru.mts.avpopo85.weathery.data.model.implementation.yandexWeather.YWWeatherResponse
import ru.mts.avpopo85.weathery.data.network.base.ForecastApiService
import ru.mts.avpopo85.weathery.data.network.base.NetworkManager
import ru.mts.avpopo85.weathery.data.utils.YW_FORECAST_PARAMETERS
import ru.mts.avpopo85.weathery.domain.repository.ForecastRepository
import ru.mts.avpopo85.weathery.utils.YWForecastResponseType
import javax.inject.Inject


class YWForecastRepository
@Inject constructor(
    private val forecastApiService: ForecastApiService<YWWeatherResponse, YWForecastParameters>,
    private val networkManager: NetworkManager,
    private val currentWeatherDbService: ForecastDbService<YWForecastResponseType>
) : ForecastRepository<YWForecastResponseType> {

    override fun getForecast(): Single<YWForecastResponseType> {
        if (!networkManager.isConnectedToInternet) {
            return currentWeatherDbService.getForecastResponse(networkManager.isConnectedToInternet)
        }

        val apiCall: Single<YWForecastResponseType> =
            forecastApiService
                .getForecast(YW_FORECAST_PARAMETERS)
                .map { it.forecasts }

        return apiCall.flatMap { currentWeatherDbService.saveForecastResponse(it) }
    }

}
