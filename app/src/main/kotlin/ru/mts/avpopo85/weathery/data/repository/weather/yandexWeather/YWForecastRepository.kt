package ru.mts.avpopo85.weathery.data.repository.weather.yandexWeather

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWForecastApiService
import ru.mts.avpopo85.weathery.data.utils.yandexWeather.YWConstants.YW_FORECAST_PARAMETERS
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListResponseType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastResponseType
import javax.inject.Inject


class YWForecastRepository
@Inject constructor(
    private val apiService: IYWForecastApiService,
    private val networkManager: NetworkManager,
    private val forecastDbService: IForecastDbService<YWForecastResponseType>,
    private val locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context
) : IForecastRepository<YWForecastListResponseType> {

    override fun getForecast(): Single<YWForecastListResponseType> {
        val dbCall =
            forecastDbService.getForecastResponse(networkManager.isConnectedToInternet)

        if (!networkManager.isConnectedToInternet) {
            return dbCall
        }

        return dbCall.onErrorResumeNext { _ ->
            apiCall().flatMap { forecastDbService.saveForecastResponse(it) }
        }
    }

    private fun apiCall(): Single<out YWForecastListResponseType> {
        val currentAddress: UserAddressType? = getCurrentAddress()

        return if (currentAddress != null) {
            val coords = currentAddress.coords

            when {
                coords.areNotNull() -> getCurrentWeather(coords!!, currentAddress.countryCode!!)

                else -> Single.error(Throwable("Текущее местоположение неизвестно"))
            }
        } else Single.error(Throwable("Текущее местоположение неизвестно"))
    }

    private fun GeographicCoordinates?.areNotNull(): Boolean =
        this != null && this.latitudeAndLongitudeAreNotNull()

    private fun getCurrentWeather(
        coords: GeographicCoordinates,
        countryCode: String
    ): Single<YWForecastListResponseType> = apiService.getForecast(
        coords.latitude!!,
        coords.longitude!!,
        countryCode.decapitalize(),
        YW_FORECAST_PARAMETERS.dayNumberInForecast,
        YW_FORECAST_PARAMETERS.withForecastForHours,
        YW_FORECAST_PARAMETERS.withExtraInformation
    )

    private fun getCurrentAddress(): UserAddressType? = try {
        locationDbService.getAddress(
            isGpsProviderEnabled = networkManager.isGpsProviderEnabled,
            isNetworkProviderEnabled = networkManager.isNetworkProviderEnabled,
            isConnectedToInternet = networkManager.isConnectedToInternet
        ).blockingGet()
    } catch (exception: Exception) {
        throw Throwable("${context.getString(R.string.db_has_no_location_data)}\n${exception.localizedMessage}")
    }

}
