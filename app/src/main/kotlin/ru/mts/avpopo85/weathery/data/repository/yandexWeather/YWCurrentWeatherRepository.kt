package ru.mts.avpopo85.weathery.data.repository.yandexWeather

import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import javax.inject.Inject

class YWCurrentWeatherRepository
@Inject constructor(
    private val apiService: IYWCurrentWeatherApiService,
    private val networkManager: NetworkManager,
    private val currentWeatherDbService: ICurrentWeatherDbService<YWCurrentWeatherResponseType>,
    private val locationDbService: ILocationDbService<UserAddressType>
) : ICurrentWeatherRepository<YWCurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<YWCurrentWeatherResponseType> {
        val dbCall =
            currentWeatherDbService.getCurrentWeatherResponse(networkManager.isConnectedToInternet)

        if (!networkManager.isConnectedToInternet) {
            return dbCall
        }

        return dbCall.onErrorResumeNext { _ ->
            apiCall().flatMap { currentWeatherDbService.saveCurrentWeatherResponse(it) }
        }
    }

    private fun apiCall(): Single<out YWCurrentWeatherResponseType> {
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
    ): Single<YWCurrentWeatherResponseType> = apiService.getCurrentWeather(
        coords.latitude!!,
        coords.longitude!!,
        countryCode.decapitalize()
    )

    private fun getCurrentAddress(): UserAddressType? = try {
        locationDbService.getLocation().blockingGet()
    } catch (exception: Exception) {
        //TODO
        throw Throwable("DB has no location data\n${exception.localizedMessage}")
    }

}
