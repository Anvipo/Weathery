package ru.mts.avpopo85.weathery.presentation.weather.tab


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_tabbed_weather.*
import kotlinx.android.synthetic.main.appbar.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.activity.withProgressBar.AbsProgressBarActivity
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsActivity
import ru.mts.avpopo85.weathery.presentation.utils.LOCALITY_TAG
import ru.mts.avpopo85.weathery.presentation.utils.SETTINGS_REQUEST
import ru.mts.avpopo85.weathery.presentation.utils.SETTING_RESULT_OK
import ru.mts.avpopo85.weathery.presentation.utils.WEATHER_API_TAG
import ru.mts.avpopo85.weathery.presentation.weather.base.view.fragment.AbsWeatherFragment
import ru.mts.avpopo85.weathery.presentation.weather.tab.adapter.SectionsPagerAdapter

class TabbedWeather : AbsProgressBarActivity() {

    override val rootLayout: View by lazy { activity_tabbed_weather_CL }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed_weather)

        setSupportActionBar(viewToolbar)

        currentLocation = sharedPreferences.getString(currentLocationPrefKey, null)
        chosenWeatherAPI = sharedPreferences.getString(weatherAPIPrefKey, null)

        activity_tabbed_weather_VP.adapter = weatherTypesPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tabbed_weather, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> {
            currentLocation = sharedPreferences.getString(currentLocationPrefKey, null)
            chosenWeatherAPI = sharedPreferences.getString(weatherAPIPrefKey, null)

            startActivityForResult<SettingsActivity>(SETTINGS_REQUEST)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            SETTINGS_REQUEST -> onSettingsRequest(resultCode, data)
            else -> onUnexpectedApplicationBehavior()
        }
    }

    private val weatherTypesPagerAdapter: SectionsPagerAdapter by lazy {
        SectionsPagerAdapter(
            fragmentManager = this@TabbedWeather.supportFragmentManager!!,
            context = this@TabbedWeather.applicationContext!!,
            rootLayout = this@TabbedWeather.rootLayout
        )
    }

    private val viewToolbar: Toolbar by lazy { toolbar }

    private val sharedPreferences: SharedPreferences
            by lazy { PreferenceManager.getDefaultSharedPreferences(this)!! }

    private val currentLocationPrefKey: String by lazy { getString(R.string.pref_key_current_location) }

    private val weatherAPIPrefKey: String by lazy { getString(R.string.pref_key_weather_API) }

    private var currentLocation: String? = null

    private var chosenWeatherAPI: String? = null

    private fun onSettingsRequest(resultCode: Int, data: Intent?) {
        when (resultCode) {
            SETTING_RESULT_OK -> {
                if (currentLocation != null) {
                    val coordinates = data?.getStringExtra(LOCALITY_TAG)

                    if (coordinates != currentLocation) {
                        //TODO
//                        repeat(weatherTypesPagerAdapter.count) {
//                            val item = weatherTypesPagerAdapter.getItem(it) as AbsWeatherFragment
//
//                            item.onNewLocation()
//                        }
                    }
                }

                if (chosenWeatherAPI != null) {
                    val weatherAPI = data?.getStringExtra(WEATHER_API_TAG)

                    if (weatherAPI != chosenWeatherAPI) {
                        //TODO
                        //do work

                        val c = 1
                    }

                    val c = 1
                }
            }
            else -> onUnexpectedApplicationBehavior()
        }
    }

}
