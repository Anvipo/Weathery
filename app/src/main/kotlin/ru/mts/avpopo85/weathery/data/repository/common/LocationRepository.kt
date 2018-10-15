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
import ru.mts.avpopo85.weathery.data.network.utils.IGeocoder
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.common.utils.ONE_SECOND_IN_MILLIS
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import ru.mts.avpopo85.weathery.utils.common.ExtractAddressException
import ru.mts.avpopo85.weathery.utils.common.GpsCallException.*
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LocationRepository
@Inject constructor(
    private val context: Context,
    private val dbService: ILocationDbService<UserAddressType>,
    private val networkManager: NetworkManager,
    private val geocoder: IGeocoder
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

//      return  onServiceIsNotAvailable(location, IOException(""))

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

    private fun makeGpsCall(success: Boolean): Single<Location> =
        when {
            success && networkManager.isConnectedToInternet -> getCurrentLocation()
            success && !networkManager.isConnectedToInternet -> onHaveSuccessAndDeviceIsNotConnectedToInternet()
            !success && networkManager.isConnectedToInternet -> onHaveNotSuccessAndDeviceIsConnectedToInternet()
            !success && !networkManager.isConnectedToInternet -> onHaveNotSuccessAndDeviceIsNotConnectedToInternet()
            else -> onUnknownError()
        }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): Single<Location> =
        rxLocation.location()
            .updates(locationRequest)
            .timeout(10, TimeUnit.SECONDS)
            .firstOrError()

    private fun makeAddressFromLocation(location: Location): Single<UserAddressType> =
        if (networkManager.isConnectedToInternet) {
            getAddressFromLocation(location)
        } else {
            onDeviceIsNotConnectedToInternet()
        }

    private fun getAddressFromLocation(location: Location): Single<UserAddressType> =
        rxLocation.geocoding()
            .fromLocation(location)
            .map(::mapUserAddress)
            .flatMap(::checkLocalityOnNull)
            .toSingle()
            .onErrorResumeNext { onGeocodeError(it, location) }

    private fun onGeocodeError(
        error: Throwable,
        location: Location
    ): Single<UserAddressType> =
        when (error) {
            is NoSuchElementException -> onNoSuchElementException()
            is IOException -> onServiceIsNotAvailable(location, error)
            else -> Single.error(error)
        }

    private fun onNoSuchElementException(): Single<UserAddressType> {
        //on unknown address
        val error = ExtractAddressException("NoSuchElement")

        return Single.error(error)
    }

    private fun checkLocalityOnNull(it: UserAddressType): Maybe<UserAddressType> =
        if (it.locality == null) {
            val error = ExtractAddressException("Locality is null")

            Maybe.error(error)
        } else {
            Maybe.just(it)
        }

    private fun onServiceIsNotAvailable(
        location: Location,
        error: IOException
    ): Single<UserAddressType> {
        error.printStackTrace()

        return geocoder.geocodeLocation(location).flatMap(dbService::saveCurrentAddress)
    }

    private fun mapUserAddress(address: Address): UserAddressType =
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

    private fun onDeviceIsNotConnectedToInternet(): Single<UserAddressType> {
        val message = context.getString(R.string.internet_connection_required)
        val error = DeviceIsNotConnectedToInternetException(message)

        return Single.error(error)
    }

    private fun <T> onHaveSuccessAndDeviceIsNotConnectedToInternet(): Single<T> {
        val message = context.getString(R.string.internet_connection_required)
        val error = HaveSuccessAndDeviceIsNotConnectedException(message)

        return Single.error(error)
    }

    private fun onUnknownError(): Single<Location> {
        val message = context.getString(R.string.unknown_error)
        val error = UnknownErrorException(message)

        return Single.error(error)
    }

    private fun onHaveNotSuccessAndDeviceIsConnectedToInternet(): Single<Location> {
        val message = context.getString(R.string.gps_is_required)
        val error = HaveNotSuccessAndDeviceIsConnectedToInternet(message)

        return Single.error(error)
    }

    private fun onHaveNotSuccessAndDeviceIsNotConnectedToInternet(): Single<Location> {
        val message = context.getString(R.string.internet_connection_and_GPS_required)
        val error = HaveNotSuccessAndDeviceIsNotConnectedToInternet(message)

        return Single.error(error)
    }

}