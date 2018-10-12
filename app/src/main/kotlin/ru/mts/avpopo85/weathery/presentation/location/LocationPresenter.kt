package ru.mts.avpopo85.weathery.presentation.location

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.google.android.gms.maps.model.LatLng
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.utils.ExtractAddressException
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract
import ru.mts.avpopo85.weathery.presentation.utils.APPLICATION_SETTINGS_REQUEST_CODE
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import javax.inject.Inject

class LocationPresenter
@Inject constructor(
    private var context: LocationActivity?,
    private val interactor: ILocationInteractor,
    private val schedulerManagerModule: SchedulerManagerModule
) : AbsBasePresenter<LocationContract.View>(),
    LocationContract.Presenter {

    override fun onUnbindView() {
        super.onUnbindView()

        context = null
    }

    override fun onGoSettingsPositiveClick() {
        // Build intent that displays the App settings screen.
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        }

        context!!.startActivityForResult(intent, APPLICATION_SETTINGS_REQUEST_CODE)
    }

    override fun onGoSettingNegativeClick() {
        view?.showLocationError()
    }

    override fun getCurrentGeolocation() {
        checkLocationPermissions()
    }

    override fun getLastKnownGeolocation() {
        val task = interactor.getLastKnownAddress()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::onSuccessGetLastKnownAddress, ::onErrorGetLastKnownAddress)

        compositeDisposable.add(task)
    }

    override fun onActivityResult() {
        checkLocationPermissions()
    }

    override fun onRationalePositiveClick() {
        checkLocationPermissions()
    }

    override fun onRationaleNegativeClick() {
        view?.showLocationError()
    }

    override fun getAddressFromCoordinates(coordinates: LatLng) {
        val task = interactor.getAddressFromCoordinates(coordinates)
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::onSuccessGetAddressFromCoordinates, ::onErrorGetAddressFromCoordinates)

        compositeDisposable.add(task)
    }

    private fun onSuccessGetLastKnownAddress(address: UserAddressType) {
        if (address.locality != null) {
            view?.showCityDialog(address.locality!!)
        } else {
            view?.disableGetLastKnownLocationButton()
            view?.showLastKnownLocationError()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onErrorGetLastKnownAddress(error: Throwable) {
        view?.disableGetLastKnownLocationButton()
        view?.showLastKnownLocationError()
    }

    private fun onSuccessGetAddressFromCoordinates(address: UserAddressType) {
        if (address.locality != null) {
            view?.showCityDialog(address.locality!!)
        } else {
            val error = ExtractAddressException("Невозможно узнать адрес указанного местоположения")

            view?.showGetAddressFromCoordinatesError(error)
        }
    }

    private fun onErrorGetAddressFromCoordinates(error: Throwable) {
        view?.showGetAddressFromCoordinatesError(error)
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
                val task = interactor.getCurrentAddressByGPS()
                    .compose(schedulerManagerModule.singleTransformer())
                    .doAfterTerminate { view?.hideLoadingProgress() }
                    .subscribe(::onSuccessGetCurrentAddressByGPS, ::onErrorGetCurrentAddressByGPS)

                compositeDisposable.add(task)
            }

            permission.shouldShowRequestPermissionRationale -> {
                //user denied permission, but dont tap "dont ask"
                view?.hideLoadingProgress()

                view?.showRationaleDialog()
            }

            else -> {
                //user denied permission with tap on "dont ask"
                view?.hideLoadingProgress()

                view?.showGoSettingsDialog()
            }
        }
    }

    private fun onSuccessGetCurrentAddressByGPS(address: UserAddressType) {
        when {
            address.locality != null -> {
                view?.showCityDialog(address.locality!!)
                view?.enableGetLastKnownLocationButton()
            }
            else -> view?.showLocationError()
        }
    }

    private fun onErrorGetCurrentAddressByGPS(error: Throwable) {
        val message: String =
            error.localizedMessage ?: error.message ?: context!!.getString(R.string.unknown_error)

        view?.showError(message)
    }

}