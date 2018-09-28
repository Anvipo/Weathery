package ru.mts.avpopo85.weathery.data.repository.common

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.LocationRequest
import com.patloew.rxlocation.RxLocation
import io.reactivex.Observable
import io.reactivex.Single
import ru.mts.avpopo85.weathery.domain.repository.ILocationRepository
import javax.inject.Inject


class LocationRepository
@Inject constructor(context: Context) : ILocationRepository {

    private val rxLocation by lazy { RxLocation(context) }

    private val locationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val locationRequest by lazy {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000)
    }

    override fun kek(): Observable<Address> = rxLocation
        .settings()
        .checkAndHandleResolution(locationRequest)
        .flatMapObservable(this::getAddressObservable)
        .take(1)

    @SuppressLint("MissingPermission")
    private fun getAddressObservable(success: Boolean): Observable<Address> {
        return if (success) {
            val c = 1

            rxLocation.location()
                .updates(locationRequest)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext(view::onLocationUpdate)
                .flatMap(this::getAddressFromLocation)
        } else {
            val c = 1
//            view.onLocationSettingsUnsuccessful()

            rxLocation.location()
                .lastLocation()
//                .doOnSuccess(view::onLocationUpdate)
                .flatMapObservable(this::getAddressFromLocation)
        }
    }

    private fun getAddressFromLocation(location: Location): Observable<Address> =
        rxLocation.geocoding()
            .fromLocation(location)
            .toObservable()

    @SuppressLint("MissingPermission")
    override fun getLocation(): Observable<Location> {
        val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val emptyLoc = Observable.create<Location> { emitter ->
            if (!networkEnabled || !gpsEnabled) {
                emitter.onNext(Location(""))
            }
        }

        val loc = Observable.merge(
            rxLocation.location().lastLocation().toObservable(),
            rxLocation.location().updates(locationRequest).take(1)
        ).take(1)

        return Observable.merge(loc, emptyLoc)
    }

    @SuppressLint("MissingPermission")
    override fun getNewLocation(): Single<Location> {
        val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        return if (!networkEnabled || !gpsEnabled) {
            Single.error(Throwable("Error locations. Providers not enabled"))
        } else {
            Single.fromObservable(rxLocation.location().updates(locationRequest).take(1))
        }
    }

}