package ru.mts.avpopo85.weathery.data.repository.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.patloew.rxlocation.RxLocation
import io.reactivex.Maybe
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserAddress
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserLocale
import ru.mts.avpopo85.weathery.data.network.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.utils.ONE_SECOND_IN_MILLIS
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import ru.mts.avpopo85.weathery.utils.common.MyRealmException.InternetConnectionIsRequiredException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import java.util.*
import javax.inject.Inject


class LocationRepository
@Inject constructor(
    private val context: Context,
    private val dbService: ILocationDbService<UserAddressType>,
    private val networkManager: NetworkManager
) : ILocationRepository {

    override fun getCurrentAddressByGPS(): Single<UserAddressType> =
        rxLocation
            .settings()
            .checkAndHandleResolution(locationRequest)
            .flatMap(::getCurrentAddressByGPS)

    override fun getLastKnownAddress(): Single<UserAddressType> =
        dbService.getLastKnownAddress(
            networkManager.isGpsProviderEnabled,
            networkManager.isNetworkProviderEnabled,
            networkManager.isConnectedToInternet
        )

    override fun getAddressFromCoordinates(coordinates: LatLng): Single<UserAddressType> {
        val location = Location(coordinates.toString()).apply {
            latitude = coordinates.latitude
            longitude = coordinates.longitude
        }

        return makeAddressFromLocation(location).flatMap(dbService::saveCurrentAddress)
    }

    private val rxLocation by lazy { RxLocation(context) }

    private val locationRequest by lazy {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(10L * ONE_SECOND_IN_MILLIS)
            .setFastestInterval(5L * ONE_SECOND_IN_MILLIS)
    }

    private fun getCurrentAddressByGPS(success: Boolean): Single<UserAddressType> =
        makeGpsCall(success)
            .flatMap(::makeAddressFromLocation)
            .flatMap(dbService::saveCurrentAddress)

    @SuppressLint("MissingPermission")
    private fun makeGpsCall(success: Boolean): Single<Location> =
        if (success && networkManager.isConnectedToInternet) {
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
        } else {
            val message = context.getString(R.string.internet_connection_required)
            val error = InternetConnectionIsRequiredException(message)

            Single.error(error)
        }

    private fun getAddressFromLocation(location: Location): Single<UserAddressType> =
        rxLocation.geocoding()
            .fromLocation(location)
            .map(::mapUserAddress)
            .flatMap {
                if (it.locality == null) {
                    val extractAddressException =
                        ExtractAddressException("Невозможно узнать адрес указанного местоположения")

                    Maybe.error<UserAddressType>(extractAddressException)
                } else {
                    Maybe.just(it)
                }
            }
            .toSingle()
            .onErrorResumeNext { error: Throwable ->
                if (error is NoSuchElementException) {
                    val extractAddressException =
                        ExtractAddressException("Невозможно узнать адрес указанного местоположения")

                    Single.error(extractAddressException)
                } else {
                    Single.error(error)
                }
            }

    class ExtractAddressException(message: String) : Throwable(message)

    private fun mapUserAddress(address: Address): UserAddress =
        address.let {
            UserAddress(
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
                postalCode = it.postalCode?.toInt(),
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

}