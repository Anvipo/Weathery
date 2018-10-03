package ru.mts.avpopo85.weathery.presentation.location

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_location.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.data.utils.UserAddressType
import ru.mts.avpopo85.weathery.di.modules.common.LocationModule
import ru.mts.avpopo85.weathery.presentation.base.AbsBaseActivity
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract
import javax.inject.Inject


@Suppress("PrivatePropertyName")
class LocationActivity : AbsBaseActivity(), LocationContract.View {

    @Inject
    lateinit var presenter: LocationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        setSupportActionBar(toolbar)

        App.appComponent
            .plus(LocationModule(this))
            .inject(this)

        presenter.onBindView(this)

        get_location_LA_B.setOnClickListener {
            presenter.getLocation()
        }
    }

    override fun onResume() {
        super.onResume()

        checkPlayServicesAvailable()
    }

    override fun showLocation(address: UserAddressType) {
        other_info.text = address.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PHONE_SETTINGS_REQUEST_CODE)
            if (resultCode == 0)
                presenter.onActivityResult()
    }

    override fun showRationaleDialog() {
        alert(
            "Для установки местоположения приложению необходимы права доступа к геопозиции. Предоставить?",
            "Внимание"
        ) {
            yesButton {
                presenter.onRationalePositiveClick()
            }
            noButton {
                presenter.onRationaleNegativeClick()
            }
        }.show()
    }

    override fun showGoSettingsDialog() {
        alert(
            "Для установки местоположения необходимо перейти в настройки и включить разрешение доступа к геопозиции. Перейти?",
            "Внимание"
        ) {
            yesButton {
                presenter.onGoSettingsPositiveClick()
            }
            noButton {
                presenter.onGoSettingNegativeClick()
            }
        }.show()
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            REQUEST_CHECK_SETTINGS -> locationUpdatesHelper.onActivityResult(resultCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            FROM_LOCATION_UPDATES -> locationUpdatesHelper.onRequestPermissionsResult(grantResults)
            FROM_LAST_KNOWN_LOCATION -> lastKnownLocationHelper
                .onRequestPermissionsResult(grantResults)
        }
    }*/

    private fun checkPlayServicesAvailable() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val status = apiAvailability.isGooglePlayServicesAvailable(this)

        if (status != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(status)) {
                apiAvailability.getErrorDialog(this, status, 1).show()
            } else {
                val message = "${getString(R.string.google_play_services_unavailable)}. " +
                        getString(R.string.this_app_will_not_work)

                findViewById<View>(android.R.id.content).indefiniteSnackbar(message)
            }
        }
    }

}
