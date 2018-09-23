package ru.mts.avpopo85.weathery.data.repository.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWForecastApiService
import ru.mts.avpopo85.weathery.data.utils.YW_FORECAST_PARAMETERS
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.YWForecastListResponseType
import javax.inject.Inject


class YWForecastRepository
@Inject constructor(
    private val ywForecastApiService: IYWForecastApiService,
    private val networkManager: NetworkManager,
    private val currentWeatherDbService: IForecastDbService<YWForecastListResponseType>
) : IForecastRepository<YWForecastListResponseType> {

    override fun getForecast(): Single<YWForecastListResponseType> {
        if (!networkManager.isConnectedToInternet) {
            return currentWeatherDbService.getForecastResponse(networkManager.isConnectedToInternet)
        }

        val apiCall: Single<YWForecastListResponseType> =
            ywForecastApiService
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
