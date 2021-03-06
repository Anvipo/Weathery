package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherRealmResponse
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager

abstract class AbsCurrentWeatherRepository<T : ICurrentWeatherRealmResponse>(
    private val currentWeatherDbService: ICurrentWeatherDbService<T>,
    private val networkManager: NetworkManager,
    locationDbService: ILocationDbService,
    context: Context
) : AbsWeatherRepository<T>(locationDbService, context, networkManager) {

    protected abstract fun makeApiCall(): Single<T>

    protected fun getCurrentWeatherHelper(): Single<T> =
        if (networkManager.isConnectedToInternet) {
            makeDBCall().onErrorResumeNext(::onDbCallError)
        } else {
            makeDBCall()
        }

    //TODO
    private fun makeDBCall(): Single<T> =
        currentWeatherDbService
            .getCurrentWeatherResponse(networkManager.isConnectedToInternet)
            .flatMap {
                doChecks(it)

                Single.just(it)
            }

    @Suppress("UNUSED_PARAMETER")
    private fun onDbCallError(error: Throwable): Single<T> = makeApiCall().flatMap(::saveInDB)

    private fun saveInDB(currentWeatherResponse: T): Single<T> =
        currentWeatherDbService.saveCurrentWeatherResponse(currentWeatherResponse)

}
