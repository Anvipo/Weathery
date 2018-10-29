package ru.mts.avpopo85.weathery.presentation.location.implementation

import com.google.android.gms.maps.model.LatLng
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationInteractor
import ru.mts.avpopo85.weathery.presentation.base.common.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract
import ru.mts.avpopo85.weathery.utils.common.ExtractAddressException
import ru.mts.avpopo85.weathery.utils.common.GoogleGeocodeException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import javax.inject.Inject

class LocationPresenter
@Inject constructor(
    private val interactor: ILocationInteractor,
    private val schedulerManagerModule: SchedulerManagerModule
) : AbsBasePresenter<LocationContract.View>(),
    LocationContract.Presenter {

    override fun onGoSettingsForGetCurrentLocationPositiveClick() {
        view?.startSettingsActivityForResult()
    }

    override fun onGoSettingForGetCurrentLocationNegativeClick() {
        view?.showLocationError()
    }

    override fun onGetCurrentLocationRationalePositiveClick() {
        checkLocationPermissions()
    }

    override fun onGetCurrentLocationRationaleNegativeClick() {
        view?.showLocationError()
    }

    override fun onGetCurrentGeolocationByGPSClick() {
        val task: Disposable = interactor.checkInternetConnection()
            .compose(schedulerManagerModule.completableTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .subscribe(
                ::onSuccessCheckInternetConnectionForGetCurrentLocationByGPS,
                ::onErrorCheckInternetConnectionForGetCurrentLocationByGPS
            )

        compositeDisposable.add(task)
    }

    override fun onGetLastKnownLocationClick() {
        val task: Disposable = interactor.getLastKnownAddress()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doFinally { view?.hideLoadingProgress() }
            .subscribe(::onSuccessGetLastKnownAddress, ::onErrorGetLastKnownAddress)

        compositeDisposable.add(task)
    }

    override fun onGetCurrentLocationByMapClick() {
        val task: Disposable = interactor.checkInternetConnection()
            .compose(schedulerManagerModule.completableTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doFinally { view?.hideLoadingProgress() }
            .subscribe(
                ::onSuccessCheckInternetConnectionForGetCurrentLocationByMap,
                ::onErrorCheckInternetConnectionForGetCurrentLocationByMap
            )

        compositeDisposable.add(task)
    }

    override fun onLocationByMapsRequestActivityResult(coordinates: LatLng) {
        val task: Disposable = interactor.getAddressFromCoordinates(coordinates)
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doFinally { view?.hideLoadingProgress() }
            .subscribe(::onSuccessGetAddressFromCoordinates, ::onErrorGetAddressFromCoordinates)

        compositeDisposable.add(task)
    }

    override fun saveAddress(address: UserAddressType) {
        val task: Disposable = interactor.saveAddress(address)
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doFinally { view?.hideLoadingProgress() }
            .subscribe({}, { view?.showError(it) })

        compositeDisposable.add(task)
    }

    override fun onApplicationSettingsRequestForGetCurrentLocationActivityResult() {
        checkLocationPermissions()
    }

    private fun onErrorCheckInternetConnectionForGetCurrentLocationByGPS(error: Throwable) {
        view?.hideLoadingProgress()
        view?.showError(error)
    }

    private fun onSuccessCheckInternetConnectionForGetCurrentLocationByGPS() {
        checkLocationPermissions()
    }

    private fun onSuccessCheckInternetConnectionForGetCurrentLocationByMap() {
        view?.getCurrentLocationByMap()
    }

    private fun onErrorCheckInternetConnectionForGetCurrentLocationByMap(error: Throwable) {
        view?.showError(error)
    }

    private fun onSuccessGetLastKnownAddress(address: UserAddressType) {
        if (address.locality != null) {
            view?.showCityDialog(address)
        } else {
            view?.disableGetLastKnownLocationButton()
            view?.showLastKnownLocationError()
        }
    }

    private fun onErrorGetLastKnownAddress(error: Throwable) {
        error.printStackTrace()

        view?.disableGetLastKnownLocationButton()
        view?.showLastKnownLocationError()
    }

    private fun onSuccessGetAddressFromCoordinates(address: UserAddressType) {
        if (address.locality != null) {
            view?.showCityDialog(address)
        } else {
            val error = ExtractAddressException("Locality is null")

            onErrorGetAddressFromCoordinates(error)
        }
    }

    private fun onErrorGetAddressFromCoordinates(error: Throwable) {
        if (error is GoogleGeocodeException) {
            view?.showError(error)
        } else {
            view?.showGetAddressFromCoordinatesError(error)
        }
    }

    private fun checkLocationPermissions() {
        val task: Disposable = interactor.requestLocationPermissions()
            .compose(schedulerManagerModule.observableTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .subscribe(::onSuccessRequestLocationPermissions, ::onErrorRequestLocationPermissions)

        compositeDisposable.add(task)
    }

    private fun onErrorRequestLocationPermissions(error: Throwable) {
        view?.hideLoadingProgress()

        view?.showError(error)
    }

    private fun onSuccessRequestLocationPermissions(permission: Permission) {
        when {
            permission.granted -> {
                val task: Disposable = interactor.getCurrentAddressByGPS()
                    .compose(schedulerManagerModule.singleTransformer())
                    .doFinally { view?.hideLoadingProgress() }
                    .subscribe(::onSuccessGetCurrentAddressByGPS, ::onErrorGetCurrentAddressByGPS)

                compositeDisposable.add(task)
            }

            permission.shouldShowRequestPermissionRationale -> {
                //user denied permission, but dont tap "dont ask"
                view?.hideLoadingProgress()

                view?.showGetCurrentLocationRationaleDialog()
            }

            else -> {
                //user denied permission with tap on "dont ask"
                view?.hideLoadingProgress()

                view?.showGoSettingsForGetCurrentLocationDialog()
            }
        }
    }

    private fun onSuccessGetCurrentAddressByGPS(address: UserAddressType) {
        if (address.locality != null) {
            view?.enableGetLastKnownLocationButton()
            view?.setResultAndFinish(address)
        } else {
            view?.showLocationError()
        }
    }

    private fun onErrorGetCurrentAddressByGPS(error: Throwable) {
        view?.showError(error)
    }

}
