package ru.mts.avpopo85.weathery.data.repository.openWeatherMap

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.openWeatherMap.forecast.OWMForecastResponse
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.openWeatherMap.IOWMForecastApiService
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.domain.repository.IForecastRepository
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListResponseType
import javax.inject.Inject


class OWMForecastRepository
@Inject constructor(
    private val apiService: IOWMForecastApiService,
    private val networkManager: NetworkManager,
    private val forecastDbService: IForecastDbService<OWMForecastListResponseType>,
    private val locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context
) : IForecastRepository<OWMForecastListResponseType> {

    override fun getForecast(): Single<OWMForecastListResponseType> {
        val dbCall = forecastDbService
            .getForecastResponse(networkManager.isConnectedToInternet)

        if (!networkManager.isConnectedToInternet) {
            return dbCall
        }

        return dbCall.onErrorResumeNext { _ ->
            apiCall()
                ?.map { it.forecastsList }
                ?.flatMap { forecastDbService.saveForecastResponse(it) }
        }
    }

    private fun apiCall(): Single<out OWMForecastResponse>? {
        val currentAddress = getCurrentAddress()

        return if (currentAddress != null) {
            val cityName = currentAddress.locality

            val coords = currentAddress.coords

            val postalCode = currentAddress.postalCode

            when {
                cityName != null -> getCurrentWeatherByCityName(cityName)

                coords.areNotNull() -> getCurrentWeatherByGeographicCoordinates(coords!!)

                postalCode != null -> getCurrentWeatherByZipCode(
                    postalCode,
                    currentAddress.countryCode
                )

                else -> Single.error(Throwable("Текущее местоположение неизвестно"))
            }
        } else {
            Single.error(Throwable("Текущее местоположение неизвестно"))
        }
    }

    private fun GeographicCoordinates?.areNotNull(): Boolean =
        this != null && this.latitudeAndLongitudeAreNotNull()

    private fun getCurrentWeatherByZipCode(
        zipCode: Int,
        countryCode: String?
    ): Single<OWMForecastResponse>? {
        val zip = if (countryCode != null) "$zipCode,$countryCode" else "$zipCode"

        return apiService.getCurrentWeatherByZipCode(zip)
    }

    private fun getCurrentWeatherByGeographicCoordinates(
        coords: GeographicCoordinates
    ): Single<OWMForecastResponse> =
        apiService.getCurrentWeatherByGeographicCoordinates(coords.latitude!!, coords.longitude!!)

    private fun getCurrentWeatherByCityName(cityName: String): Single<OWMForecastResponse> =
        apiService.getCurrentWeatherByCityName(cityName)

    private fun getCurrentAddress(): UserAddressType? = try {
        locationDbService.getLocation().blockingGet()
    } catch (exception: Exception) {
        throw Throwable("${context.getString(R.string.db_has_no_location_data)}\n${exception.localizedMessage}")
    }

}
