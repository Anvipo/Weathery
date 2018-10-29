package ru.mts.avpopo85.weathery.presentation.weather.tab


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_tabbed_weather.*
import kotlinx.android.synthetic.main.appbar.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.activity.withProgressBar.AbsProgressBarActivity
import ru.mts.avpopo85.weathery.presentation.settings.implementation.SettingsActivity
import ru.mts.avpopo85.weathery.presentation.weather.tab.adapter.SectionsPagerAdapter

class TabbedWeather : AbsProgressBarActivity() {

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

    private val weatherTypesPagerAdapter: SectionsPagerAdapter by lazy {
        SectionsPagerAdapter(
            fragmentManager = this@TabbedWeather.supportFragmentManager!!,
            context = this@TabbedWeather.applicationContext!!,
            rootLayout = this@TabbedWeather.rootLayout
        )
    }

    private val viewToolbar: Toolbar by lazy { toolbar }

}
