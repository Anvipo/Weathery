package ru.mts.avpopo85.weathery.data.network.utils

import android.content.Context
import android.location.Location
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode.AddressComponentsItem
import ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode.GoogleGeocoderResponse
import ru.mts.avpopo85.weathery.data.model.implementation.common.googleGeocode.ResultsItem
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
        OK -> onOkResponse(response, emitter)
        UNKNOWN_ERROR, INVALID_REQUEST, REQUEST_DENIED, OVER_QUERY_LIMIT, OVER_DAILY_LIMIT, ZERO_RESULTS ->
            onErrorResponse(response, emitter)
    }

    private fun onErrorResponse(
        response: GoogleGeocoderResponse,
        emitter: SingleEmitter<UserAddressType>
    ) {
        val message: String = response.errorMessage ?: context.getString(R.string.unknown_error)
        sendErrorLog(message)

        onGeocodingProblems(emitter)
    }

    private fun onOkResponse(
        response: GoogleGeocoderResponse,
        emitter: SingleEmitter<UserAddressType>
    ) {
        val userAddress = UserAddressType()

        response.results?.let { results ->
            results.forEach { result ->
                result?.let {
                    extractAddressComponents(result, userAddress)

                    extractCoordinates(result, userAddress)
                }
            }
        }

        if (userAddress.locality != null) {
            emitter.onSuccess(userAddress)
        } else {
            onGeocodingProblems(emitter)
        }
    }

    private fun extractCoordinates(
        resultItem: ResultsItem,
        userAddress: UserAddressType
    ) {
        val location = resultItem.geometry?.location

        if (location != null) {
            val geographicCoordinates = GeographicCoordinates(
                latitude = location.latitude,
                longitude = location.longitude
            )

            if (userAddress.coords == null) {
                userAddress.coords = geographicCoordinates
            }
        }
    }

    private fun extractAddressComponents(
        resultItem: ResultsItem,
        userAddress: UserAddressType
    ) {
        resultItem.addressComponents?.forEach { addressComponent ->
            val types = addressComponent?.types

            val itIsStreetNumber = types?.any { it == "street_number" }

            val itIsRoute = types?.any { it == "route" }

            val itIsLocality = types?.any { it == "locality" }

            val itIsAdministrativeAreaLevel3 =
                types?.any { it == "administrative_area_level_3" }

            val itIsAdministrativeAreaLevel2 =
                types?.any { it == "administrative_area_level_2" }

            val itIsAdministrativeAreaLevel1 =
                types?.any { it == "administrative_area_level_1" }

            val itIsCountry = types?.any { it == "country" }

            val itIsPostalCode = types?.any { it == "postal_code" }

            when {
                itIsStreetNumber == true -> onStreetNumber(userAddress, addressComponent)
                itIsRoute == true -> onRoute(userAddress, addressComponent)
                itIsLocality == true -> onLocality(userAddress, addressComponent)
                itIsAdministrativeAreaLevel3 == true ->
                    onAdministrativeAreaLevel3(userAddress, addressComponent)
                itIsAdministrativeAreaLevel2 == true ->
                    onAdministrativeAreaLevel2(userAddress, addressComponent)
                itIsAdministrativeAreaLevel1 == true ->
                    onAdministrativeAreaLevel1(userAddress, addressComponent)
                itIsCountry == true -> onCountry(userAddress, addressComponent)
                itIsPostalCode == true -> onPostalCode(userAddress, addressComponent)
            }
        }
    }

    private fun onPostalCode(
        userAddress: UserAddressType,
        addressComponent: AddressComponentsItem
    ) {
        if (userAddress.postalCode == null) {
            userAddress.postalCode = addressComponent.longName?.toInt()
        }
    }

    private fun onCountry(
        userAddress: UserAddressType,
        addressComponent: AddressComponentsItem
    ) {
        if (userAddress.countryName == null) {
            userAddress.countryName = addressComponent.longName
        }
    }

    private fun onAdministrativeAreaLevel1(
        userAddress: UserAddressType,
        addressComponent: AddressComponentsItem
    ) {
        if (userAddress.adminArea == null) {
            userAddress.adminArea = addressComponent.longName
        }
    }

    private fun onAdministrativeAreaLevel2(
        userAddress: UserAddressType,
        addressComponent: AddressComponentsItem
    ) {
        if (userAddress.subAdminArea == null) {
            userAddress.subAdminArea = addressComponent.longName
        }
    }

    private fun onAdministrativeAreaLevel3(
        userAddress: UserAddressType,
        addressComponent: AddressComponentsItem
    ) {
        if (userAddress.subLocality == null) {
            userAddress.subLocality = addressComponent.longName
        }
    }

    private fun onLocality(
        userAddress: UserAddressType,
        addressComponent: AddressComponentsItem
    ) {
        if (userAddress.locality == null) {
            userAddress.locality = addressComponent.longName
        }
    }

    private fun onRoute(
        userAddress: UserAddressType,
        addressComponent: AddressComponentsItem
    ) {
        if (userAddress.thoroughfare == null) {
            userAddress.thoroughfare = addressComponent.longName
        }
    }

    private fun onStreetNumber(
        userAddress: UserAddressType,
        addressComponent: AddressComponentsItem
    ) {
        if (userAddress.subThoroughfare == null) {
            userAddress.subThoroughfare = addressComponent.longName
        }
    }

    private fun onGeocodingProblems(emitter: SingleEmitter<UserAddressType>) {
        val part1 = context.getString(R.string.there_are_problems_geocoding)
        val part2 = context.getString(R.string.please_try_later)

        val message = "$part1. $part2"
        val error = GoogleGeocodeException(message)

        emitter.onError(error)
    }

}
