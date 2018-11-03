package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.IForecastDbService
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.base.common.IForecastRealmResponse
import ru.mts.avpopo85.weathery.data.model.base.openWeatherMap.forecast.IBaseWeatherResponse
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.weather.utils.onUnknownCurrentLocation
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class AbsForecastRepository<T : IForecastRealmResponse, R : IBaseWeatherResponse>(
    private val forecastDbService: IForecastDbService<T>,
    private val networkManager: NetworkManager,
    locationDbService: ILocationDbService,
    private val context: Context
) : AbsWeatherRepository<T>(locationDbService, context, networkManager) {

    protected abstract fun makeApiCall(): Single<List<T>>

    protected fun onNewLocationHelper(): Single<List<T>> =
        makeApiCall().flatMap(::saveInDB)

    protected fun getForecastHelper(): Single<List<T>> =
        if (!networkManager.isConnectedToInternet) {
            makeDBCall()
        } else {
            makeDBCall().onErrorResumeNext(::onDbCallError)
        }

    protected fun makeApiCall(
        firstApiCall: Single<R>?,
        secondApiCall: Single<R>?,
        thirdApiCall: Single<R>?
    ): Single<R> =
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
        first: Single<R>,
        second: Single<R>?,
        third: Single<R>?
    ): Single<R> =
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
        second: Single<R>,
        third: Single<R>?
    ): Single<R> =
        second.onErrorResumeNext {
            when {
                it is UnknownHostException || it is SocketTimeoutException -> Single.error(it)
                third != null -> thirdApiCallWithErrorCatching(third)
                else -> onUnknownLocation()
            }
        }

    private fun thirdApiCallWithErrorCatching(third: Single<R>): Single<R> =
        third.onErrorResumeNext {
            when (it) {
                is UnknownHostException, is SocketTimeoutException -> Single.error(it)
                else -> onUnknownLocation()
            }
        }

    private fun onUnknownLocation(): Single<R> =
        context.onUnknownCurrentLocation()

    private fun makeDBCall(): Single<List<T>> =
        forecastDbService
            .getForecastResponse(networkManager.isConnectedToInternet)
            .flatMap {
                //first do check (with blocking get)
                locationChangeCheck(it.first()).blockingGet()

                Single.just(it)
            }

    @Suppress("UNUSED_PARAMETER")
    private fun onDbCallError(error: Throwable): Single<List<T>> =
        makeApiCall().flatMap(::saveInDB)

    private fun saveInDB(currentWeatherResponse: List<T>): Single<List<T>> =
        forecastDbService.saveForecastResponse(currentWeatherResponse)

}
