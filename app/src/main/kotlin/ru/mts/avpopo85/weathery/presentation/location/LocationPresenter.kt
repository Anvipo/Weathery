package ru.mts.avpopo85.weathery.presentation.location

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.google.android.gms.maps.model.LatLng
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract
import ru.mts.avpopo85.weathery.presentation.utils.APPLICATION_SETTINGS_REQUEST_CODE
import ru.mts.avpopo85.weathery.presentation.utils.onParameterIsNull
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
        checkPermissions()
    }

    override fun getLastKnownGeolocation() {
        val task = interactor.getLastKnownAddress()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::getLastKnownAddressOnSuccess, ::getLastKnownAddressOnError)

        compositeDisposable.add(task)
    }

    override fun onActivityResult() {
        checkPermissions()
    }

    override fun onRationalePositiveClick() {
        checkPermissions()
    }

    override fun onRationaleNegativeClick() {
        view?.showLocationError()
    }

    override fun getAddressFromCoordinates(coordinates: LatLng) {
        val task = interactor.getAddressFromCoordinates(coordinates)
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .doAfterTerminate { view?.hideLoadingProgress() }
            .subscribe(::getAddressFromCoordinatesOnSuccess, ::getAddressFromCoordinatesOnError)

        compositeDisposable.add(task)
    }

    private fun getAddressFromCoordinatesOnSuccess(address: UserAddressType?) {
        if (address != null) {
            if (address.locality != null) {
                view?.showCityDialog(address.locality!!)
            } else {
                view?.showGetAddressFromCoordinatesError()
            }
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "getAddressFromCoordinatesOnSuccess"

                onParameterIsNull(
                    view,
                    this::class.java.simpleName,
                    methodName,
                    "address"
                )
            }
        }
    }

    private fun getAddressFromCoordinatesOnError(error: Throwable?) {
        if (error != null) {
            view?.showGetAddressFromCoordinatesError()
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "getAddressFromCoordinatesOnError"

                onParameterIsNull(
                    view,
                    this::class.java.simpleName,
                    methodName,
                    "error"
                )
            }
        }
    }

    private fun getLastKnownAddressOnSuccess(address: UserAddressType?) {
        if (address != null) {
            if (address.locality != null) {
                view?.showCityDialog(address.locality!!)
            } else {
                view?.disableGetLastKnownLocationButton()
                view?.showLastKnownLocationError()
            }
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "getLastKnownAddressOnSuccess"

                onParameterIsNull(
                    view,
                    this::class.java.simpleName,
                    methodName,
                    "address"
                )

            }
        }
    }

    private fun getLastKnownAddressOnError(error: Throwable?) {
        if (error != null) {
            view?.disableGetLastKnownLocationButton()
            view?.showLastKnownLocationError()
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "getLastKnownAddressOnError"

                onParameterIsNull(
                    view,
                    this::class.java.simpleName,
                    methodName,
                    "error"
                )
            }
        }
    }

    private fun checkPermissions() {
        val task: Disposable = interactor.requestPermissions()
            .compose(schedulerManagerModule.observableTransformer())
            .doOnSubscribe { view?.showLoadingProgress() }
            .subscribe(::requestPermissionsOnSuccess, ::requestPermissionsOnError)

        compositeDisposable.add(task)
    }

    private fun requestPermissionsOnSuccess(permission: Permission?) {
        if (permission != null) {
            onPermissionSuccess(permission)
        } else {
            view?.hideLoadingProgress()

            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "requestPermissionsOnSuccess"

                onParameterIsNull(
                    view,
                    this::class.java.simpleName,
                    methodName,
                    "permission"
                )
            }
        }
    }

    private fun requestPermissionsOnError(error: Throwable?) {
        view?.hideLoadingProgress()

        if (error != null) {
            view?.showError(error)
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "requestPermissionsOnError"

                onParameterIsNull(
                    view,
                    this::class.java.simpleName,
                    methodName,
                    "error"
                )
            }
        }
    }

    private fun onPermissionSuccess(permission: Permission) {
        when {
            permission.granted -> {
                val task = interactor.getCurrentAddress()
                    .compose(schedulerManagerModule.singleTransformer())
                    .doAfterTerminate { view?.hideLoadingProgress() }
                    .subscribe(::getCurrentAddressOnSuccess, ::getCurrentAddressOnError)

                compositeDisposable.add(task)
            }

            permission.shouldShowRequestPermissionRationale -> {
                view?.hideLoadingProgress()

                view?.showRationaleDialog()
            }

            else -> {
                view?.hideLoadingProgress()

                view?.showGoSettingsDialog()
            }
        }
    }

    private fun getCurrentAddressOnSuccess(address: UserAddressType?) {
        if (address != null) {
            when {
                address.locality != null -> {
                    view?.showCityDialog(address.locality!!)
                    view?.enableGetLastKnownLocationButton()
                }
                else -> view?.showLocationError()
            }
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "getCurrentAddressOnSuccess"

                onParameterIsNull(
                    view,
                    this::class.java.simpleName,
                    methodName,
                    "address"
                )
            }
        }
    }

    private fun getCurrentAddressOnError(error: Throwable?) {
        if (error != null) {
            view?.showError(error)
        } else {
            if (BuildConfig.DEBUG) {
                val methodName =
                    object : Any() {}.javaClass.enclosingMethod?.name
                        ?: "getCurrentAddressOnError"

                onParameterIsNull(view, this::class.java.simpleName, methodName, "error")
            }
        }
    }

}