package ru.mts.avpopo85.weathery.data.repository.weather.common

import android.content.Context
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.base.common.IWeatherResponse
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.weather.utils.PreviousLocationUnknownException
import ru.mts.avpopo85.weathery.data.repository.weather.utils.onUnknownCurrentLocation
import ru.mts.avpopo85.weathery.data.repository.weather.utils.onUnknownError
import ru.mts.avpopo85.weathery.utils.common.MyRealmException
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.DBHasOutdatedWeatherDataException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import java.net.ProtocolException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.math.abs

abstract class AbsWeatherRepository<T : IWeatherResponse>(
    private val locationDbService: ILocationDbService,
    private val context: Context,
    private val networkManager: NetworkManager
) {

    protected fun getLastKnownAddress(): UserAddressType =
        try {
            locationDbService.getLastKnownAddress(
                isGpsProviderEnabled = networkManager.isGpsProviderEnabled,
                isNetworkProviderEnabled = networkManager.isNetworkProviderEnabled,
                isConnectedToInternet = networkManager.isConnectedToInternet
            ).blockingGet()
        } catch (exception: Throwable) {
            val cause = exception.cause ?: exception

            val message: String = if (cause is MyRealmException.DBHasNoCurrentAddressException) {
                cause.localizedMessage ?: cause.message ?: context.getString(R.string.unknown_error)
            } else {
                val part1 = context.getString(R.string.previous_location_unknown)

                val part2 = cause.localizedMessage ?: cause.message

                if (part2 != null)
                    "$part1\n$part2"
                else
                    part1
            }

            throw PreviousLocationUnknownException(message)
        }

    protected fun doChecks(weatherData: T) {
        with(context) {
            checkFreshness(weatherData)

            locationChangeCheck(weatherData)
        }
    }

    protected fun <D> makeApiCall(
        firstApiCall: Single<D>?,
        secondApiCall: Single<D>?,
        thirdApiCall: Single<D>?
    ): Single<D> =
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

    private fun <D> firstApiCallWithErrorCatching(
        first: Single<D>,
        second: Single<D>?,
        third: Single<D>?
    ): Single<D> =
        first.onErrorResumeNext {
            when {
                it is UnknownHostException ||
                        it is SocketTimeoutException ||
                        it is ProtocolException -> Single.error(it)
                second != null ->
                    secondApiCallWithErrorCatching(second, third)
                third != null -> thirdApiCallWithErrorCatching(third)
                else -> onUnknownError(it)
            }
        }

    private fun <D> secondApiCallWithErrorCatching(
        second: Single<D>,
        third: Single<D>?
    ): Single<D> =
        second.onErrorResumeNext {
            when {
                it is UnknownHostException ||
                        it is SocketTimeoutException ||
                        it is ProtocolException -> Single.error(it)
                third != null -> thirdApiCallWithErrorCatching(third)
                else -> onUnknownError(it)
            }
        }

    private fun <D> thirdApiCallWithErrorCatching(third: Single<D>): Single<D> =
        third.onErrorResumeNext {
            when (it) {
                is UnknownHostException,
                is SocketTimeoutException,
                is ProtocolException -> Single.error(it)
                else -> onUnknownError(it)
            }
        }

    private fun <D> onUnknownLocation(): Single<D> =
        context.onUnknownCurrentLocation()

    private fun <D> onUnknownError(throwable: Throwable): Single<D> =
        context.onUnknownError(throwable)

    private fun checkFreshness(weatherData: T) {
        val c = 1
    }

    private fun Context.locationChangeCheck(weatherData: T) {
        val previousCoords = weatherData.coordinates

        val currentCoords = getLastKnownAddress().coords

        if (previousCoords != null && currentCoords != null) {
            val latitudeDiff = abs(previousCoords.latitude - currentCoords.latitude)
            val longitudeDiff = abs(previousCoords.longitude - currentCoords.longitude)

            val locationHasChanged = latitudeDiff > 0.1 || longitudeDiff > 0.1

            if (locationHasChanged) {
                val part1 = getString(R.string.your_location_has_changed)

                val part2 = if (networkManager.isConnectedToInternet) {
                    getString(R.string.get_data_from_server)
                } else {
                    getString(R.string.internet_connection_required)
                }

                val message = "$part1. $part2"

                throw DBHasOutdatedWeatherDataException(
                    message,
                    networkManager.isConnectedToInternet
                )
            }
        }

    }
}
