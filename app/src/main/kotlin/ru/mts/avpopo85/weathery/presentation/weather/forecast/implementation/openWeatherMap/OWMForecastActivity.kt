package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View.GONE
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_owm_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMForecastModule
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.OWMForecastActivityPagerAdapter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import javax.inject.Inject

class OWMForecastActivity : AbsForecastActivity<ViewPager, OWMForecastListType>(),
    ForecastContract.View<OWMForecastListType> {

    @Inject
    lateinit var presenter: ForecastContract.Presenter<OWMForecastListType>

    override val progressBar: ProgressBar by lazy { owm_forecast_PB }

    override val view: ViewPager by lazy { owm_forecast_pager }

    private lateinit var pagerAdapter: OWMForecastActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owm_forecast)

        App.appComponent
            .plus(OWMForecastModule(this))
            .inject(this)

        initPager()

        presenter.onBindView(this)
        presenter.loadForecast()
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
    }

    override fun showWeatherResponse(data: OWMForecastListType) {
        if (data.isNotEmpty()) {
            showLayout()
            putForecastDataInPager(data)
        } else
            hideLayout()
    }

    override fun putForecastDataInPager(data: OWMForecastListType) {
        for (forecast in data) {
            val yfr = OWMForecastFragment()

            yfr.arguments = Bundle().apply {
                putParcelable(ARG_FORECAST, forecast)
            }

            pagerAdapter.addFragment(yfr, forecast.date)
        }

        pagerAdapter.notifyDataSetChanged()
    }

    private fun initPager() {
        pagerAdapter = OWMForecastActivityPagerAdapter(supportFragmentManager)
        owm_forecast_pager.adapter = pagerAdapter
        owm_forecast_pager.visibility = GONE
    }

}
