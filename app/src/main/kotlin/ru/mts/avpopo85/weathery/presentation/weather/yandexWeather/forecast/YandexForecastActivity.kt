package ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_yandex_forecast.*
import org.jetbrains.anko.longToast
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.weather.yandexWeather.YandexWeatherModule
import ru.mts.avpopo85.weathery.models.weather.yandexWeather.Forecast
import ru.mts.avpopo85.weathery.presentation.weather.yandexWeather.forecast.adapters.YandexForecastAdapter
import javax.inject.Inject

class YandexForecastActivity : AppCompatActivity(), ForecastContract.ForecastView {
    @Inject
    lateinit var yandexForecastPresenter: YandexForecastPresenter

    private lateinit var yandexForecastAdapter: YandexForecastAdapter

//    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_forecast)

        App.appComponentForYandexWeather.plus(YandexWeatherModule()).inject(this)
        yandexForecastPresenter.onBindView(this)

        initRecyclerViews()

        yandexForecastPresenter.onStart()
    }

    private fun initRecyclerViews() {
        //TODO ViewPager
//        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
//
//        viewPager.adapter = viewPagerAdapter

        yandexForecastAdapter = YandexForecastAdapter()

        yandex_forecast_recycler_view.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(
                this@YandexForecastActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = yandexForecastAdapter
        }
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
        yandexForecastAdapter.addItems(data)

        //TODO ViewPager
        /*for (forecast in data) {
            val yfr = YandexForecastFragment()
            yfr.yandexForecastAdapter.addItem(forecast)
            viewPagerAdapter.addFragment(yfr, forecast.date_ts)
        }*/
    }

    override fun showError(throwable: Throwable) {
        val str = throwable.message ?: ""
        longToast(str)
    }

    override fun showError(message: String) {
        longToast(message)
    }

    //TODO ViewPager
    /*class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {
        private val items = mutableListOf<Fragment>()
        private val tabTitles = mutableListOf<String>()

        fun addFragment(fragment: Fragment, title: String) {
            items.add(fragment)
            tabTitles.add(title)
        }

        override fun getItem(position: Int): Fragment = items[position]

        override fun getCount(): Int = items.size

        override fun getPageTitle(position: Int) = tabTitles[position]
    }*/
}