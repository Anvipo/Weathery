package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_yandex_forecast.yandex_forecast_PB
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.domain.Forecast
import ru.mts.avpopo85.weathery.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.utils.makeTitle
import javax.inject.Inject


class YandexForecastActivity : AppCompatActivity(), ForecastContract.ForecastView {
    override val context: Context = this

    @Inject
    lateinit var yandexForecastPresenter: YandexForecastPresenter

    private lateinit var mPager: ViewPager

    private lateinit var mPagerAdapter: ScreenSlidePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_forecast)

        App.appComponentForYandexWeather.plus(YandexWeatherModule()).inject(this)
        yandexForecastPresenter.onBindView(this)

        yandexForecastPresenter.onStart()
    }

    override fun onDestroy() {
        yandexForecastPresenter.onUnbindView()
        super.onDestroy()
    }

    override fun showLoadingProgress() {
        yandex_forecast_PB.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        yandex_forecast_PB.visibility = View.GONE
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

            mPagerAdapter.addFragment(yfr, title)
        }
    }

    private fun initPager() {
        mPager = findViewById(R.id.pager)
        mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = mPagerAdapter
    }

    override fun showError(throwable: Throwable) {
        val str = throwable.message ?: ""
        longToast(str)
    }

    override fun showError(message: String?) {
        if (message != null)
            longToast(message)
    }

    private class ScreenSlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {

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