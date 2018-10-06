package ru.mts.avpopo85.weathery.data.repository.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import io.reactivex.Single
import ru.mts.avpopo85.weathery.data.db.base.ILocationDbService
import ru.mts.avpopo85.weathery.data.model.implementation.common.GeographicCoordinates
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserAddress
import ru.mts.avpopo85.weathery.data.model.implementation.common.UserLocale
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import java.util.*
import javax.inject.Inject


class LocationRepository
@Inject constructor(
    private val context: Context,
    private val dbService: ILocationDbService<UserAddressType>
) : ILocationRepository {

    private val rxLocation by lazy { RxLocation(context) }

    private val locationRequest by lazy {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000)
    }

    override fun getCurrentAddress(): Single<UserAddressType> =
        rxLocation
            .settings()
            .checkAndHandleResolution(locationRequest)
            .flatMap(::getAddress)

    override fun getLastKnownAddress(): Single<UserAddressType> {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        return dbService.getLocation(gpsIsEnabled)
    }

    @SuppressLint("MissingPermission")
    private fun getAddress(gpsIsEnabled: Boolean): Single<UserAddressType> {
        val gpsCall = if (gpsIsEnabled) {
            rxLocation.location()
                .updates(locationRequest)
                .firstOrError()
        } else {
            rxLocation.location()
                .lastLocation()
                .toSingle()
        }

        return gpsCall
            .flatMap(::getAddressFromLocation)
            .flatMap { dbService.saveLocation(it) }
            .onErrorResumeNext { dbService.getLocation(gpsIsEnabled) }
    }

    private fun getAddressFromLocation(location: Location): Single<UserAddressType> =
        rxLocation.geocoding()
            .fromLocation(location)
            .map(::mapUserAddress)
            .toSingle()

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