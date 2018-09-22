package ru.mts.avpopo85.weathery.data.repository.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ForecastDbService
import ru.mts.avpopo85.weathery.data.network.base.NetworkManager
import ru.mts.avpopo85.weathery.data.network.implementation.yandexWeather.YWForecastApiService
import ru.mts.avpopo85.weathery.data.utils.YW_FORECAST_PARAMETERS
import ru.mts.avpopo85.weathery.domain.repository.ForecastRepository
import ru.mts.avpopo85.weathery.utils.ForecastListResponseType
import javax.inject.Inject


class YWForecastRepository
@Inject constructor(
    private val forecastApiService: YWForecastApiService,
    private val networkManager: NetworkManager,
    private val currentWeatherDbService: ForecastDbService<ForecastListResponseType>
) : ForecastRepository<ForecastListResponseType> {

    override fun getForecast(): Single<ForecastListResponseType> {
        if (!networkManager.isConnectedToInternet) {
            return currentWeatherDbService.getForecastResponse(networkManager.isConnectedToInternet)
        }

        val apiCall: Single<ForecastListResponseType> =
            forecastApiService
                .getForecast(
                    YW_FORECAST_PARAMETERS.latitude,
                    YW_FORECAST_PARAMETERS.longitude,
                    YW_FORECAST_PARAMETERS.language,
                    YW_FORECAST_PARAMETERS.dayNumberInForecast,
                    YW_FORECAST_PARAMETERS.withForecastForHours,
                    YW_FORECAST_PARAMETERS.withExtraInformation
                )
                .map { it.forecasts }

        return apiCall.flatMap { currentWeatherDbService.saveForecastResponse(it) }
    }

}
