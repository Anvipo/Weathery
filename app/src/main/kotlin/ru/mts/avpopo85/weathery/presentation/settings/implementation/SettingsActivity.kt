package ru.mts.avpopo85.weathery.presentation.settings.implementation

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.RingtonePreference
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragment
import androidx.preference.PreferenceManager
import io.reactivex.exceptions.CompositeException
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.data.repository.weather.utils.PreviousLocationUnknownException
import ru.mts.avpopo85.weathery.di.modules.common.SettingsModule
import ru.mts.avpopo85.weathery.presentation.settings.base.SettingsContract
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.LocationPreferenceFragment
import ru.mts.avpopo85.weathery.presentation.settings.implementation.fragments.NetworkPreferenceFragment
import ru.mts.avpopo85.weathery.presentation.utils.LOCALITY_TAG
import ru.mts.avpopo85.weathery.presentation.utils.LOCATION_RESULT_OK
import ru.mts.avpopo85.weathery.presentation.utils.WEATHER_API_TAG
import ru.mts.avpopo85.weathery.utils.common.*
import javax.inject.Inject

class SettingsActivity : PreferenceActivity(), SettingsContract.View {

    @Inject
    lateinit var presenter: SettingsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initInjection()
        bindPresenter()
    }

    override fun onDestroy() {
        unbindPresenter()
        super.onDestroy()
    }

    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.preference_headers, target)
    }

    override fun onBackPressed() {
        if (hasHeaders()) {
            onBackPressedInHeaders()
        } else {
            onBackPressedInFragment()
        }
    }

    override fun onIsMultiPane(): Boolean = isXLargeTablet(this)

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean =
        (PreferenceFragment::class.java.name == fragmentName
                || LocationPreferenceFragment::class.java.name == fragmentName
                || NetworkPreferenceFragment::class.java.name == fragmentName)

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        setupActionBar()
    }

    override fun onUnexpectedApplicationBehavior(rootView: View?) = Unit

    override fun showShortSnackbar(message: String, rootView: View?) {
        val view = rootView ?: rootLayout

        view.showShortSnackbar(message)
    }

    override fun showLongSnackbar(message: String, rootView: View?) {
        val view = rootView ?: rootLayout

        view.showLongSnackbar(message)
    }

    override fun showIndefiniteSnackbar(message: String, rootView: View?) {
        val view = rootView ?: rootLayout

        view.showIndefiniteSnackbar(message)
    }

    override fun changeTitle(title: String) = Unit

    override fun showError(message: String, isCritical: Boolean, rootView: View?) {
        if (!isCritical) {
            showLongSnackbar(message, rootView)
        } else {
            showIndefiniteSnackbar(message, rootView)
        }
    }

    override fun showError(error: Throwable, isCritical: Boolean, rootView: View?) {
        error.printStackTrace()

        val cause = if (error is CompositeException) {
            error.exceptions.last()!!
        } else {
            error
        }

        sendErrorLog(cause.toString())

        val message = parseError(cause)

        sendErrorLog(message)

        val internetConnectionRequired =
            if (cause is MyRealmException.DBHasNoWeatherResponseException) {
                !cause.isConnectedToInternet
            } else {
                false
            }

        val _isCritical =
            cause is PreviousLocationUnknownException || isCritical || internetConnectionRequired

        showError(message, _isCritical, rootView)
    }

    override fun onSuccessCheckPreferences(settingsInfo: SettingsInfo) {
        val intent = Intent().apply {
            putExtra(LOCALITY_TAG, settingsInfo.currentLocation)
            putExtra(WEATHER_API_TAG, settingsInfo.chosenWeatherAPI)
        }

        setResult(LOCATION_RESULT_OK, intent)

        super.onBackPressed()
    }

    override fun notifyAbout(error: Throwable) = Unit

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

    private fun onBackPressedInHeaders() {
        presenter.checkPreferences()
    }

    private fun onBackPressedInFragment() {
        super.onBackPressed()
    }

    private fun setupActionBar() {
        rootLayout.addView(toolbar, 0) // insert at top

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private val toolbar: Toolbar by lazy {
        LayoutInflater
            .from(this)
            .inflate(R.layout.settings_toolbar, rootLayout, false) as Toolbar
    }

    private val rootLayout: LinearLayout by lazy {
        findViewById<View>(android.R.id.list).parent.parent.parent as LinearLayout
    }

    companion object {

        /**
         * Helper method to determine if the device has an extra-large screen. For
         * example, 10" tablets are extra-large.
         */
        private fun isXLargeTablet(context: Context): Boolean =
            context.resources.configuration.screenLayout and
                    Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE

        private val bindPreferenceSummaryToValueListener =
            Preference.OnPreferenceChangeListener { preference, value ->
                val stringValue = value.toString()

                if (preference is ListPreference) {
                    val index = preference.findIndexOfValue(stringValue)

                    val summaryValue = if (index >= 0)
                        preference.entryValues[index]
                    else
                        null

                    preference.summary = summaryValue
                    preference.value = summaryValue?.toString()
                } else if (preference is RingtonePreference) {
                    if (TextUtils.isEmpty(stringValue)) {
                        preference.setSummary(R.string.pref_ringtone_silent)
                    } else {
                        val ringtone = RingtoneManager
                            .getRingtone(preference.getContext(), Uri.parse(stringValue))

                        if (ringtone == null) {
                            preference.setSummary(null)
                        } else {
                            val name = ringtone.getTitle(preference.getContext())
                            preference.setSummary(name)
                        }
                    }
                } else preference.summary = stringValue

                true
            }

        fun bindPreferenceSummaryToValue(
            preference: Preference,
            defaultValue: String
        ) {
            preference.onPreferenceChangeListener = bindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference's
            // current value.
            bindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager
                    .getDefaultSharedPreferences(preference.context)
                    .getString(preference.key, defaultValue)
            )
        }

    }
}
