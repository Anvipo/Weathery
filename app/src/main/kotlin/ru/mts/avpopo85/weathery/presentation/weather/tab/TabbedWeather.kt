package ru.mts.avpopo85.weathery.presentation.weather.tab


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_tabbed_weather.*
import kotlinx.android.synthetic.main.appbar.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.activity.withProgressBar.AbsProgressBarActivity
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.implementation.openWeatherMap.view.fragment.OWMCurrentWeatherFragment
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.view.fragment.OWMForecastFragment

class TabbedWeather : AbsProgressBarActivity() {

    override val rootLayout: View by lazy { activity_tabbed_weather_CL }

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private val viewToolbar: Toolbar by lazy { toolbar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed_weather)

        setSupportActionBar(viewToolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        activity_tabbed_weather_VP.adapter = mSectionsPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tabbed_weather, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> true
        else -> super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> OWMCurrentWeatherFragment()
            else -> OWMForecastFragment()
        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> getString(R.string.current_weather)
            else -> getString(R.string.forecast)
        }

    }

}
