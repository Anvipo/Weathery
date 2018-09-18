package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_yandex_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.domain.models.Forecast
import ru.mts.avpopo85.weathery.presentation.weather.WeatherActivity
import ru.mts.avpopo85.weathery.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.utils.makeTitle
import javax.inject.Inject


class YandexForecastActivity : WeatherActivity(), ForecastContract.View {

    override val progressBar: ProgressBar by lazy { yandex_forecast_PB }

    @Inject
    lateinit var presenter: ForecastContract.Presenter

    private lateinit var pager: ViewPager

    private lateinit var pagerAdapter: ScreenSlidePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_forecast)

        App.appComponentForYandexWeather.plus(YandexWeatherModule()).inject(this)

        presenter.onBindView(this)
        presenter.onStart()
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
    }

    override fun showWeatherResponse(data: List<Forecast>) {
        initPager()

        putForecastDataInPager(data)
    }

    private fun putForecastDataInPager(data: List<Forecast>) {
        for (forecast in data) {
            val yfr = YandexForecastFragment()

            yfr.arguments = Bundle().apply {
                putParcelable(ARG_FORECAST, forecast)
            }

            val title = makeTitle(forecast)

            pagerAdapter.addFragment(yfr, title)
        }
    }

    private fun initPager() {
        pager = findViewById(R.id.pager)
        pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter
    }

    private class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val items = mutableListOf<Fragment>()
        private val tabTitles = mutableListOf<String>()

        override fun getItem(position: Int): Fragment = items[position]

        override fun getCount(): Int = items.size

        override fun getPageTitle(position: Int): CharSequence? = tabTitles[position]

        fun addFragment(fragment: Fragment, title: String) {
            items.add(fragment)
            tabTitles.add(title)
            notifyDataSetChanged()
        }
    }

}
