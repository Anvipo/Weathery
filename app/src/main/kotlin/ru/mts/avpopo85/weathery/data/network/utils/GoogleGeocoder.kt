package ru.mts.avpopo85.weathery.data.network.utils

import android.content.Context
import android.location.Location
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode.GoogleGeocoderResponse
import ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode.utils.GoogleGeocodeStatus.*
import ru.mts.avpopo85.weathery.data.network.retrofit.location.IGoogleGeocoderApiService
import ru.mts.avpopo85.weathery.utils.common.GoogleGeocodeException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.common.sendErrorLog
import javax.inject.Inject

class GoogleGeocoder
@Inject constructor(
    private val geocoderApiService: IGoogleGeocoderApiService,
    private val context: Context
) : IGeocoder {

    override fun geocodeLocation(location: Location): Single<UserAddressType> =
        Single.create { emitter ->
            val apiKey = context.getString(R.string.google_maps_key)

            val latlng = "${location.latitude},${location.longitude}"

            geocoderApiService.geocodeByLatLng(latlng, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { onSuccessGoogleGeocoding(it, emitter) },
                    emitter::onError
                )
        }

    private fun onSuccessGoogleGeocoding(
        response: GoogleGeocoderResponse,
        emitter: SingleEmitter<UserAddressType>
    ) = when (response.status) {
        OK -> {
            val userAddress = UserAddressType()

            var success = false

            response.results?.let { results ->
                results.forEach { result ->
                    result?.let {
                        success = true

                        val c = it

                        val location = it.geometry?.location
                        val geographicCoordinates = GeographicCoordinates(
                            latitude = location?.lat,
                            longitude = location?.lng
                        )

                        userAddress.coords = geographicCoordinates

                        val o = 1
                    }
                }
            }

            if (success) {
                emitter.onSuccess(userAddress)
            } else {
                emitter.onError(Throwable(""))
            }
        }
        UNKNOWN_ERROR,
        INVALID_REQUEST,
        REQUEST_DENIED,
        OVER_QUERY_LIMIT,
        OVER_DAILY_LIMIT,
        ZERO_RESULTS -> {
            val message: String = response.errorMessage ?: context.getString(R.string.unknown_error)
            val googleGeocodeException = GoogleGeocodeException(message)

            sendErrorLog(message)

            emitter.onError(googleGeocodeException)
        }
    }

}