package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.utils.common.UserAddressType

abstract class AbsForecastRepository<T : IForecastRealmResponse>(
    private val networkManager: NetworkManager,
    private val forecastDbService: IForecastDbService<T>,
    locationDbService: ILocationDbService<UserAddressType>,
    context: Context
) : AbsWeatherRepository(locationDbService, context, networkManager) {

    protected abstract fun makeApiCall(): Single<List<T>>

    protected fun getForecastHelper(): Single<List<T>> =
        if (!networkManager.isConnectedToInternet) {
            makeDBCall()
        } else {
            makeDBCall().onErrorResumeNext(::onDbCallError)
        }

    private fun makeDBCall(): Single<List<T>> =
        forecastDbService.getForecastResponse(networkManager.isConnectedToInternet)

    @Suppress("UNUSED_PARAMETER")
    private fun onDbCallError(error: Throwable): Single<List<T>> =
        makeApiCall().flatMap(::saveInDB)

    private fun saveInDB(currentWeatherResponse: List<T>): Single<List<T>> =
        forecastDbService.saveForecastResponse(currentWeatherResponse)

}