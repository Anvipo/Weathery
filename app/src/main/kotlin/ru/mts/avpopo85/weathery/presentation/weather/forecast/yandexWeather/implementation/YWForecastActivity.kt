package ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.implementation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_yandex_forecast.yandex_forecast_PB
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.YandexWeatherModule
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.utils.makeTitle
import ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.base.ForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.yandexWeather.base.ForecastContract
import ru.mts.avpopo85.weathery.utils.ForecastListType
import ru.mts.avpopo85.weathery.utils.ForecastType
import javax.inject.Inject


class YWForecastActivity : ForecastActivity(),
    ForecastContract.View {

    @Inject
    lateinit var forecastPresenter: ForecastContract.Presenter

    override val progressBar: ProgressBar by lazy { yandex_forecast_PB }

    private lateinit var pager: ViewPager

    private lateinit var pagerAdapter: ScreenSlidePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_forecast)

        App.appComponentForYandexWeather.plus(YandexWeatherModule())
            .inject(this)

        forecastPresenter.onBindView(this)
        forecastPresenter.loadForecast()
    }

    override fun onDestroy() {
        forecastPresenter.onUnbindView()
        super.onDestroy()
    }

    override fun showWeatherResponse(data: ForecastListType) {
        initPager()

        putForecastDataInPager(data)
    }

    private fun putForecastDataInPager(data: List<ForecastType>) {
        for (forecast in data) {
            val yfr = YWForecastFragment()

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
