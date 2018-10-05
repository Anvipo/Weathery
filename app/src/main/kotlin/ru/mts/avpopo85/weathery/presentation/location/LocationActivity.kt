package ru.mts.avpopo85.weathery.presentation.location

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import kotlinx.android.synthetic.main.activity_location.*
import org.jetbrains.anko.startActivity
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.common.LocationModule
import ru.mts.avpopo85.weathery.presentation.base.AbsBaseActivity
import ru.mts.avpopo85.weathery.presentation.location.base.LocationContract
import ru.mts.avpopo85.weathery.presentation.main.MainActivity
import javax.inject.Inject


class LocationActivity : AbsBaseActivity(), LocationContract.View {

    @Inject
    lateinit var presenter: LocationContract.Presenter

    override val progressBar: ProgressBar by lazy { location_PB }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        setSupportActionBar(toolbar)

        App.appComponent
            .plus(LocationModule(this))
            .inject(this)

        presenter.onBindView(this)

        get_last_known_location_LA_B.setOnClickListener {
            presenter.getLastKnownGeolocation()
        }

        get_current_location_by_GPS_LA_B.setOnClickListener {
            presenter.getCurrentGeolocation()
        }
    }

    override fun showLocationDialog(city: String?) {
        //TODO
        if (city != null) {
            showAlertDialog(
                "Ваше текущее местоположение - $city?",
                "Да",
                "Нет",
                { startActivity<MainActivity>(); finish() },
                { },
                "Найдено предполагаемое местоположение"
            )
        } else {
            //TODO выключать gps
            showLongToast("Не удалось узнать местоположение")
        }
    }

    override fun onResume() {
        super.onResume()

        checkPlayServicesAvailable()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == APPLICATION_SETTINGS_REQUEST_CODE)
            if (resultCode == 0)
                presenter.onActivityResult()
    }

    override fun showRationaleDialog() {
        //TODO
        showAlertDialog(
            "Для установки местоположения приложению необходимы права доступа к геопозиции. Предоставить?",
            "Да",
            "Нет",
            { presenter.onRationalePositiveClick() },
            { presenter.onRationaleNegativeClick() },
            "Продолжение работы невозможно"
        )
    }

    override fun showGoSettingsDialog() {
        //TODO
        showAlertDialog(
            "Для установки местоположения необходимо перейти в настройки " +
                    "и включить разрешение доступа к геопозиции. Перейти?",
            "Да",
            "Нет",
            { presenter.onGoSettingsPositiveClick() },
            { presenter.onGoSettingNegativeClick() },
            "Продолжение работы невозможно"
        )
    }

    private fun checkPlayServicesAvailable() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val status = apiAvailability.isGooglePlayServicesAvailable(this)

        if (status != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(status)) {
                apiAvailability.getErrorDialog(this, status, 1).show()
            } else {
                val message = "${getString(R.string.google_play_services_unavailable)}. " +
                        getString(R.string.this_app_will_not_work)

                showIndefiniteSnackbar(message, findViewById(android.R.id.content))
            }
        }
    }

}
