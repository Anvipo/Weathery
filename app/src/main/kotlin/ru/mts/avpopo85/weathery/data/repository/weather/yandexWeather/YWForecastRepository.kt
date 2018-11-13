package ru.mts.avpopo85.weathery.data.repository.weather.yandexWeather

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWForecastApiService
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.weather.common.AbsForecastRepository
import ru.mts.avpopo85.weathery.data.repository.weather.utils.onUnknownCurrentLocation
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWConstants.YW_FORECAST_PARAMETERS
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType
import javax.inject.Inject


class YWForecastRepository
@Inject constructor(
    private val apiService: IYWForecastApiService,
    networkManager: NetworkManager,
    forecastDbService: IForecastDbService<YWForecastResponseType>,
    locationDbService: ILocationDbService,
    private val context: Context
) :
    AbsForecastRepository<YWForecastResponseType>(
        forecastDbService,
        networkManager,
        locationDbService,
        context
    ),
    IForecastRepository<YWForecastListResponseType> {

    override fun onNewLocation(): Single<YWForecastListResponseType> = onNewLocationHelper()

    override fun getForecast(): Single<YWForecastListResponseType> = getForecastHelper()

    override fun makeApiCall(): Single<YWForecastListResponseType> {
        val lastKnownAddress: UserAddressType? = getLastKnownAddress()

        return if (lastKnownAddress != null) {
            val coords: GeographicCoordinates? = lastKnownAddress.coords

            if (coords != null) {
                getCurrentWeather(coords, lastKnownAddress.countryCode!!)
            } else {
                context.onUnknownCurrentLocation()
            }
        } else {
            context.onUnknownCurrentLocation()
        }
    }

    private fun getCurrentWeather(
        coords: GeographicCoordinates,
        countryCode: String
    ): Single<YWForecastListResponseType> = apiService.getForecast(
        coords.latitude,
        coords.longitude,
        countryCode.decapitalize(),
        YW_FORECAST_PARAMETERS.dayNumberInForecast,
        YW_FORECAST_PARAMETERS.withForecastForHours,
        YW_FORECAST_PARAMETERS.withExtraInformation
    )

}
