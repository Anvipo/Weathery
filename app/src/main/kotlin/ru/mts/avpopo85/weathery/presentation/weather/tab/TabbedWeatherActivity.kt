package ru.mts.avpopo85.weathery.presentation.weather.tab


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_tabbed_weather.activity_tabbed_weather_CL
import kotlinx.android.synthetic.main.activity_tabbed_weather.activity_tabbed_weather_VP
import kotlinx.android.synthetic.main.appbar.toolbar
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.activity.withProgressBar.AbsProgressBarActivity
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.view.activity.OWMForecastInfoActivity
import ru.mts.avpopo85.weathery.presentation.weather.tab.adapter.SectionsPagerAdapter
import ru.mts.avpopo85.weathery.utils.common.startActivity
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastType

class TabbedWeatherActivity : AbsProgressBarActivity() {

    override val rootLayout: View by lazy { activity_tabbed_weather_CL }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed_weather)

        setSupportActionBar(viewToolbar)

        activity_tabbed_weather_VP.adapter = weatherTypesPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tabbed_weather, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> {
            startActivity<SettingsActivity>()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun changeTitle(title: String) = Unit

    val clickListener: (Pair<String, OWMForecastType>) -> Unit =
        { startActivity<OWMForecastInfoActivity>(it) }

    private val weatherTypesPagerAdapter: SectionsPagerAdapter by lazy {
        SectionsPagerAdapter(
            fragmentManager = this@TabbedWeatherActivity.supportFragmentManager!!,
            context = this@TabbedWeatherActivity.applicationContext!!,
            rootLayout = this@TabbedWeatherActivity.rootLayout
        )
    }

    private val viewToolbar: Toolbar by lazy { toolbar }

    companion object {

        var snackbarIsShownOrQueued: Boolean = false

    }

}
