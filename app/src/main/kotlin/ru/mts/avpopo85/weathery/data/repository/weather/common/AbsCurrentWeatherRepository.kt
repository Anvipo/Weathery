package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ICurrentWeatherDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.base.common.ICurrentWeatherRealmResponse
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.weather.utils.onUnknownCurrentLocation
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class AbsCurrentWeatherRepository<T : ICurrentWeatherRealmResponse>(
    private val currentWeatherDbService: ICurrentWeatherDbService<T>,
    private val networkManager: NetworkManager,
    locationDbService: ILocationDbService,
    private val context: Context
) : AbsWeatherRepository<T>(locationDbService, context, networkManager) {

    protected abstract fun makeApiCall(): Single<T>

    protected fun getCurrentWeatherHelper(): Single<T> =
        if (networkManager.isConnectedToInternet) {
            makeDBCall().onErrorResumeNext(::onDbCallError)
        } else {
            makeDBCall()
        }

    private fun makeDBCall(): Single<T> =
        currentWeatherDbService
            .getCurrentWeatherResponse(networkManager.isConnectedToInternet)
            .flatMap(::locationChangeCheck)

    protected fun makeApiCall(
        firstApiCall: Single<T>?,
        secondApiCall: Single<T>?,
        thirdApiCall: Single<T>?
    ): Single<T> =
        when {
            //Priority order
            //The order is important
            //DONT CHANGE ANY ORDER (CHANGE ORDER ONLY IN make3ApiCalls)
            firstApiCall != null ->
                firstApiCallWithErrorCatching(firstApiCall, secondApiCall, thirdApiCall)
            secondApiCall != null ->
                secondApiCallWithErrorCatching(secondApiCall, thirdApiCall)
            thirdApiCall != null -> thirdApiCallWithErrorCatching(thirdApiCall)
            else -> onUnknownLocation()
        }

    private fun firstApiCallWithErrorCatching(
        first: Single<T>,
        second: Single<T>?,
        third: Single<T>?
    ): Single<T> =
        first.onErrorResumeNext {
            when {
                it is UnknownHostException || it is SocketTimeoutException -> Single.error(it)
                second != null ->
                    secondApiCallWithErrorCatching(second, third)
                third != null -> thirdApiCallWithErrorCatching(third)
                else -> onUnknownLocation()
            }
        }

    private fun secondApiCallWithErrorCatching(
        second: Single<T>,
        third: Single<T>?
    ): Single<T> =
        second.onErrorResumeNext {
            when {
                it is UnknownHostException || it is SocketTimeoutException -> Single.error(it)
                third != null -> thirdApiCallWithErrorCatching(third)
                else -> onUnknownLocation()
            }
        }

    private fun thirdApiCallWithErrorCatching(third: Single<T>): Single<T> =
        third.onErrorResumeNext {
            when (it) {
                is UnknownHostException, is SocketTimeoutException -> Single.error(it)
                else -> onUnknownLocation()
            }
        }

    private fun onUnknownLocation(): Single<T> =
        context.onUnknownCurrentLocation()

    @Suppress("UNUSED_PARAMETER")
    private fun onDbCallError(error: Throwable): Single<T> = makeApiCall().flatMap(::saveInDB)

    private fun saveInDB(currentWeatherResponse: T): Single<T> =
        currentWeatherDbService.saveCurrentWeatherResponse(currentWeatherResponse)

}
