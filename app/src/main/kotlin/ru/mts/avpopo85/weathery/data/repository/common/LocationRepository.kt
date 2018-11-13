package ru.mts.avpopo85.weathery.data.repository.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Location
import androidx.core.content.edit
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.patloew.rxlocation.RxLocation
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserAddress
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserLocale
import ru.mts.avpopo85.weathery.data.network.utils.NetworkManager
import ru.mts.avpopo85.weathery.data.repository.common.utils.ONE_SECOND_IN_MILLIS
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import ru.mts.avpopo85.weathery.utils.common.*
import ru.mts.avpopo85.weathery.utils.common.GpsCallException.*
import java.io.IOException
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LocationRepository
@Inject constructor(
    private val context: Context,
    private val dbService: ILocationDbService,
    private val networkManager: NetworkManager,
    private val sharedPreferences: SharedPreferences
) : ILocationRepository {

    override fun checkInternetConnection(): Completable =
        if (networkManager.isConnectedToInternet) {
            Completable.complete()
        } else {
            val message = context.getString(R.string.internet_connection_required)
            val error = DeviceIsNotConnectedToInternetException(message)

            Completable.error(error)
        }

    override fun getCurrentAddressByGPS(): Single<UserAddressType> =
        rxLocation
            .settings()
            .checkAndHandleResolution(locationRequest)
            .flatMap(::getCurrentAddressByGPS)
            .flatMap(::saveAddressToDB)
            .flatMap(::saveAddressToSharedPreferences)

    override fun getLastKnownAddress(): Single<UserAddressType> =
        dbService.getLastKnownAddress(
            networkManager.isGpsProviderEnabled,
            networkManager.isNetworkProviderEnabled,
            networkManager.isConnectedToInternet
        )

    override fun getAddressFromCoordinates(coordinates: LatLng?): Single<UserAddressType> {
        if (coordinates == null) {
            val error = ExtractAddressException(context.getString(R.string.unknown_coordinates))

            return Single.error(error)
        }

        val location = Location(coordinates.toString()).apply {
            latitude = coordinates.latitude
            longitude = coordinates.longitude
        }

        //will save data when user confirmed location
        return makeAddressFromLocation(location)
    }

    override fun saveAddressToSharedPreferences(address: UserAddressType): Single<UserAddressType> {
        val key = context.getString(R.string.pref_key_current_location)

        sharedPreferences.edit(true) {
            putString(key, address.locality)
        }

        return Single.just(address)
    }

    override fun saveAddressToDB(address: UserAddressType): Single<UserAddressType> =
        dbService.saveCurrentAddress(address)

    private val rxLocation by lazy { RxLocation(context) }

    private val locationRequest by lazy {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(10L * ONE_SECOND_IN_MILLIS)
            .setFastestInterval(5L * ONE_SECOND_IN_MILLIS)
    }

    private fun getCurrentAddressByGPS(hasPermission: Boolean): Single<UserAddressType> =
        makeGpsCall(hasPermission).flatMap(::makeAddressFromLocation)

    private fun makeGpsCall(hasPermission: Boolean): Single<Location> =
        when {
            hasPermission && networkManager.isConnectedToInternet -> getCurrentLocation()
            !hasPermission && networkManager.isConnectedToInternet -> onHaveNotSuccessAndDeviceIsConnectedToInternet()
            hasPermission && !networkManager.isConnectedToInternet -> onHaveSuccessAndDeviceIsNotConnectedToInternet()
            !hasPermission && !networkManager.isConnectedToInternet -> onHaveNotSuccessAndDeviceIsNotConnectedToInternet()
            else -> onUnknownError()
        }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): Single<Location> =
        rxLocation.location()
            .updates(locationRequest)
            .timeout(1, TimeUnit.MINUTES)
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
        if (it.locality != null) {
            Maybe.just(it)
        } else {
            val error = ExtractAddressException("Locality is null")

            Maybe.error(error)
        }

    private fun onServiceIsNotAvailable(
        location: Location,
        error: IOException
    ): Single<UserAddressType> {
        val message = "${error.message}\n$location"

        debug(message, tr = error)

        return Single.error(error)
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
                postalCode = it.postalCode,
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
        val error = HaveNotSuccessAndDeviceIsConnectedToInternetException(message)

        return Single.error(error)
    }

    private fun onHaveNotSuccessAndDeviceIsNotConnectedToInternet(): Single<Location> {
        val message = context.getString(R.string.internet_connection_and_GPS_required)
        val error = HaveNotSuccessAndDeviceIsNotConnectedToInternetException(message)

        return Single.error(error)
    }

}
