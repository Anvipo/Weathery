package ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.locationPreference

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragment
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.common.SettingsModule
import ru.mts.avpopo85.weathery.domain.model.implementation.settings.LocationInfo
import ru.mts.avpopo85.weathery.presentation.location.implementation.LocationActivity
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsActivity
import ru.mts.avpopo85.weathery.presentation.utils.ADDRESS_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_REQUEST
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_RESULT_OK
import ru.mts.avpopo85.weathery.utils.common.MyRealmException
import ru.mts.avpopo85.weathery.utils.common.UserAddressType
import ru.mts.avpopo85.weathery.utils.common.onUnexpectedApplicationBehavior
import ru.mts.avpopo85.weathery.utils.common.showLongSnackbar
import javax.inject.Inject

class LocationPreferenceFragment : PreferenceFragment(), LocationPreferenceContract.View {

    @Inject
    lateinit var presenter: LocationPreferenceContract.Presenter

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        this.rootKey = rootKey

        initInjection()

        bindPresenter()

        presenter.checkLocationInfo()

        initLateinitProperties()
    }

    override fun onSuccessCheckLocationInfo(locationInfo: LocationInfo) {
        setContentView()
        updatePreferenceSummary(locationInfo.currentLocation)
    }

    override fun onErrorCheckLocationInfo(error: Throwable) {
        setContentView()
        updatePreferenceSummary(currentGeolocationDefaultValue)

        if (error !is MyRealmException.DBHasNoCurrentAddressException) {
            showError(error = error, isCritical = true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOCATION_REQUEST) {
            if (resultCode == LOCATION_RESULT_OK) {
                findPreference(currentGeolocationPrefKey)!!.apply {
                    val address: UserAddressType? = data?.getParcelableExtra(ADDRESS_TAG)

                    val locality = address?.locality ?: currentGeolocationDefaultValue

                    summary = locality
                }
            } else {
                val sharedPreferences = preferenceManager.sharedPreferences!!

                val currentLocation =
                    sharedPreferences.getString(currentGeolocationPrefKey, null)

                if (currentLocation == null) {
                    val part1 = getString(R.string.current_location_unknown)
                    val part2 = getString(R.string.you_must_find_out_it)

                    view!!.showLongSnackbar("$part1. $part2")
                }
            }
        } else {
            activity.applicationContext!!.onUnexpectedApplicationBehavior(view!!)
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean =
        when (preference.key) {
            getString(R.string.pref_key_get_location)!! -> {
                startActivityForResult(
                    Intent(activity, LocationActivity::class.java),
                    LOCATION_REQUEST
                )
                true
            }
            else -> {
                super.onPreferenceTreeClick(preference)
            }
        }

    override fun onDestroyView() {
        unbindPresenter()
        super.onDestroyView()
    }

    override fun onUnexpectedApplicationBehavior(rootView: View?) {
        activity.onUnexpectedApplicationBehavior(rootView = rootView)
    }

    override fun showError(error: Throwable, isCritical: Boolean, rootView: View?) {
        activity.showError(error = error, isCritical = isCritical, rootView = rootView)
    }

    override fun showError(message: String, isCritical: Boolean, rootView: View?) {
        activity.showError(message = message, isCritical = isCritical, rootView = rootView)
    }

    override fun showShortSnackbar(message: String, rootView: View?) {
        activity.showShortSnackbar(message = message, rootView = rootView)
    }

    override fun showLongSnackbar(message: String, rootView: View?) {
        activity.showLongSnackbar(message = message, rootView = rootView)
    }

    override fun showIndefiniteSnackbar(message: String, rootView: View?) {
        activity.showIndefiniteSnackbar(message = message, rootView = rootView)
    }

    override fun changeTitle(title: String) {
        activity.changeTitle(title = title)
    }

    override fun notifyAbout(error: Throwable) {
        activity.notifyAbout(error = error)
    }

    private val activity: SettingsActivity by lazy { this@LocationPreferenceFragment.getActivity() as SettingsActivity }

    private var rootKey: String? = null

    private val currentGeolocationPreference: Preference by lazy {
        findPreference(currentGeolocationPrefKey)!!
    }

    private fun bindPresenter() {
        presenter.onBindView(this)
    }

    private fun unbindPresenter() {
        presenter.onUnbindView()
    }

    private fun initInjection() {
        App.appComponent
            .plus(SettingsModule())
            .inject(this)
    }

    private fun updatePreferenceSummary(currentLocation: String) {
        SettingsActivity.bindPreferenceSummaryToValue(
            currentGeolocationPreference,
            currentLocation
        )
    }

    private fun initLateinitProperties() {
        currentGeolocationDefaultValue = getString(R.string.unknown)!!

        currentGeolocationPrefKey = getString(R.string.pref_key_current_location)!!
    }

    private fun setContentView() {
        setPreferencesFromResource(R.xml.preference_location, rootKey)
    }

    companion object {

        lateinit var currentGeolocationDefaultValue: String

        lateinit var currentGeolocationPrefKey: String

    }

}