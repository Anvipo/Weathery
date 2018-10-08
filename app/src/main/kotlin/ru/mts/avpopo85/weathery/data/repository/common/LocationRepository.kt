package ru.mts.avpopo85.weathery.data.repository.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import io.reactivex.Single
import io.reactivex.SingleSource
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserAddress
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserLocale
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.*
import java.io.IOException
import java.util.*
import javax.inject.Inject


class LocationRepository
@Inject constructor(
    private val context: Context,
    private val dbService: ILocationDbService<UserAddressType>,
    private val networkManager: NetworkManager
) : ILocationRepository {

    private val rxLocation by lazy { RxLocation(context) }

    private val locationRequest by lazy {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(10_000)
            .setFastestInterval(5_000)
    }

    override fun getCurrentAddress(): Single<UserAddressType> =
        rxLocation
            .settings()
            .checkAndHandleResolution(locationRequest)
            .flatMap(::getAddress)

    override fun getLastKnownAddress(): Single<UserAddressType> =
        dbService.getAddress(networkManager.isGpsProviderEnabled)

    private fun getAddress(isGpsProviderEnabled: Boolean): Single<UserAddressType> =
        makeGpsCall(isGpsProviderEnabled)
            .flatMap { makeAddressFromLocation(it) }
            .flatMap { dbService.saveAddress(it) }
            .onErrorResumeNext { handleGpsCallError(it, isGpsProviderEnabled) }

    @SuppressLint("MissingPermission")
    private fun makeGpsCall(isGpsAndNetworkProvidersAreEnabled: Boolean): Single<Location> =
        if (isGpsAndNetworkProvidersAreEnabled /*&& networkManager.isConnectedToInternet*/) {
            //TODO
            rxLocation.location()
                .updates(locationRequest)
                .firstOrError()
        } else {
            rxLocation.location()
                .lastLocation()
                .toSingle()
        }

    private fun makeAddressFromLocation(location: Location): Single<UserAddressType> =
        if (networkManager.isConnectedToInternet) {
            getAddressFromLocation(location)
        } /*else if (networkManager.isNetworkProviderEnabled) {
            getAddressFromLocation(location)
        }*/ else {
            val coords = getGeographicCoordinates(location)

            Single.just(UserAddressType(coords = coords))
        }

    private fun getGeographicCoordinates(location: Location): GeographicCoordinates =
        GeographicCoordinates(
            latitude = location.latitude,
            longitude = location.longitude
        )

    private fun getAddressFromLocation(location: Location): Single<UserAddressType> =
        rxLocation.geocoding()
            .fromLocation(location)
            .map(::mapUserAddress)
            .toSingle()

    private fun handleGpsCallError(
        it: Throwable,
        isGpsProviderEnabled: Boolean
    ): SingleSource<UserAddressType> = when (it) {
        is IOException -> {
            //todo
            makeGpsCall(isGpsProviderEnabled)
                .flatMap {
                    val coords = getGeographicCoordinates(it)

                    Single.just(UserAddressType(coords = coords))
                }
                .flatMap { dbService.saveAddress(it) }
                .onErrorResumeNext { dbService.getAddress(isGpsProviderEnabled) }
        }
        is NoSuchElementException -> {
            //last location is unknown
            dbService.getAddress(isGpsProviderEnabled)
        }
        else -> dbService.getAddress(isGpsProviderEnabled)
    }

    private fun mapUserAddress(it: Address): UserAddress = UserAddress(
        saveDate = Date().time,
        adminArea = it.adminArea,
        countryCode = it.countryCode,
        countryName = it.countryName,
        featureName = it.featureName,
        coords = GeographicCoordinates(
            latitude = it.latitude,
            longitude = it.longitude
        ),
        locale = UserLocale(
            language = it.locale?.language,
            region = it.locale?.country
        ),
        locality = it.locality,
        postalCode = it.postalCode.toInt(),
        subAdminArea = it.subAdminArea,
        subThoroughfare = it.subThoroughfare,
        thoroughfare = it.thoroughfare,
        extras = it.extras?.toString(),
        phone = it.phone,
        premises = it.premises,
        subLocality = it.subLocality,
        url = it.url
    )

}