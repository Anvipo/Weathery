package ru.mts.avpopo85.weathery.data.repository.weather.yandexWeather

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.network.retrofit.yandexWeather.IYWCurrentWeatherApiService
import ru.mts.avpopo85.weathery.data.repository.weather.common.AbsCurrentWeatherRepository
import ru.mts.avpopo85.weathery.data.utils.LocationUnknown
import ru.mts.avpopo85.weathery.domain.repository.ICurrentWeatherRepository
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWCurrentWeatherResponseType
import javax.inject.Inject

class YWCurrentWeatherRepository
@Inject constructor(
    private val apiService: IYWCurrentWeatherApiService,
    networkManager: NetworkManager,
    currentWeatherDbService: ICurrentWeatherDbService<YWCurrentWeatherResponseType>,
    locationDbService: ILocationDbService<UserAddressType>,
    private val context: Context
) :
    AbsCurrentWeatherRepository<YWCurrentWeatherResponseType>(
        currentWeatherDbService, locationDbService, context, networkManager
    ),
    ICurrentWeatherRepository<YWCurrentWeatherResponseType> {

    override fun getCurrentWeather(): Single<YWCurrentWeatherResponseType> =
        getCurrentWeatherHelper()

    override fun makeApiCall(): Single<YWCurrentWeatherResponseType> {
        val currentAddress: UserAddressType? = getCurrentAddress()

        return if (currentAddress != null) {
            val coords = currentAddress.coords

            when {
                coords.areNotNull() -> getCurrentWeather(coords!!, currentAddress.countryCode!!)

                //TODO
                else -> {
                    val error =
                        LocationUnknown(context.getString(R.string.current_location_unknown))

                    Single.error(error)
                }
            }
        } else {
            val error = LocationUnknown(context.getString(R.string.current_location_unknown))

            Single.error(error)
        }
    }

    private fun getCurrentWeather(
        coords: GeographicCoordinates,
        countryCode: String
    ): Single<YWCurrentWeatherResponseType> =
        apiService.getCurrentWeather(
            coords.latitude!!, coords.longitude!!, countryCode.decapitalize()
        )

}
