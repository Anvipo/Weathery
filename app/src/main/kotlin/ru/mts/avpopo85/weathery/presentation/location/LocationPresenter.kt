package ru.mts.avpopo85.weathery.presentation.location

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.tbruyelle.rxpermissions2.Permission
import io.reactivex.disposables.Disposable
import ru.mts.avpopo85.weathery.BuildConfig
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.di.global.SchedulerManagerModule
import ru.mts.avpopo85.weathery.domain.interactor.base.ILocationInteractor
import ru.mts.avpopo85.weathery.presentation.base.AbsBasePresenter
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract
import javax.inject.Inject

const val PHONE_SETTINGS_REQUEST_CODE = 9000

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
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context!!.startActivityForResult(intent, PHONE_SETTINGS_REQUEST_CODE)
    }

    override fun onGoSettingNegativeClick() {
        showLocationError()
    }

    override fun getCurrentGeolocation() {
        checkPermissions()
    }

    override fun getLastKnownGeolocation() {
        val task = interactor.getLastKnownAddress()
            .compose(schedulerManagerModule.singleTransformer())
            .doOnSubscribe {
                view?.showLoadingProgress()
            }
            .doAfterTerminate {
                view?.hideLoadingProgress()
            }
            .subscribe(
                { address: UserAddressType? ->
                    if (address != null) {
                        view?.showLocationDialog(address.locality)
                    } else {
                        //TODO
                        if (BuildConfig.DEBUG) {
                            view?.showError("${this::class.java.simpleName}.getLastKnownGeolocation - address == null")
                        }
                    }
                },
                { error: Throwable? ->
                    if (error != null) {
                        view?.showLocationDialog(null)
                    } else {
                        //TODO
                        if (BuildConfig.DEBUG) {
                            view?.showError("${this::class.java.simpleName}.getLastKnownGeolocation - error == null")
                        }
                    }
                }
            )

        compositeDisposable.add(task)
    }

    override fun onActivityResult() {
        checkPermissions()
    }

    override fun onRationalePositiveClick() {
        checkPermissions()
    }

    override fun onRationaleNegativeClick() {
        showLocationError()
    }

    private fun showLocationError() {
        view?.showLongToast("Не удалось установить местоположение")
    }

    private fun checkPermissions() {
        val task: Disposable = interactor.requestPermissions()
            .compose(schedulerManagerModule.observableTransformer())
            .doOnSubscribe {
                view?.showLoadingProgress()
            }
            .doAfterTerminate {
                view?.hideLoadingProgress()
            }
            .subscribe(
                { permission: Permission? ->
                    if (permission != null) {
                        onPermissionSuccess(permission)
                    } else {
                        //TODO
                        if (BuildConfig.DEBUG) {
                            view?.showError("${this::class.java.simpleName}.checkPermissions - permission == null")
                        }
                    }
                },
                { error: Throwable? ->
                    if (error != null) {

                    } else {
                        //TODO
                        if (BuildConfig.DEBUG) {
                            view?.showError("${this::class.java.simpleName}.checkPermissions - permission == null")
                        }
                    }
                }
            )

        compositeDisposable.add(task)
    }

    private fun onPermissionSuccess(permission: Permission) {
        @Suppress("CascadeIf")
        if (permission.granted) {
            val task = interactor.getCurrentAddress()
                .compose(schedulerManagerModule.singleTransformer())
                .doOnSubscribe {
                    view?.showLoadingProgress()
                }
                .doAfterTerminate {
                    view?.hideLoadingProgress()
                }
                .subscribe(
                    { address: UserAddressType? ->
                        if (address != null) {
                            view?.showLocationDialog(address.locality)
                        } else {
                            //TODO
                            if (BuildConfig.DEBUG) {
                                view?.showError("${this::class.java.simpleName}.checkPermissions - permission == null")
                            }
                        }
                    },
                    { error: Throwable? ->
                        if (error != null) {
                            view?.showLocationDialog(null)
                        } else {
                            //never will happen (maybe)
                            if (BuildConfig.DEBUG) {
                                view?.showError("${this::class.java.simpleName}.checkPermissions - permission == null")
                            }
                        }
                    }
                )

            compositeDisposable.add(task)
        } else if (permission.shouldShowRequestPermissionRationale) {
            view?.showRationaleDialog()
        } else {
            view?.showGoSettingsDialog()
        }
    }


}