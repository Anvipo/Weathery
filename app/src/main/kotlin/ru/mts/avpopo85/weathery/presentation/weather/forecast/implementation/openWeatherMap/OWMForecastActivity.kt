package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.os.Bundle
import android.view.View.GONE
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_owm_forecast.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_owm_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.openWeatherMap.OWMForecastModule
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.OWMForecastActivityPagerAdapter
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import javax.inject.Inject

class OWMForecastActivity :
    AbsForecastActivity<ViewPager, OWMForecastListType>(),
    ForecastContract.View<OWMForecastListType> {

    @Inject
    lateinit var presenter: ForecastContract.Presenter<OWMForecastListType>

    override val view: ViewPager by lazy { owm_forecast_pager }

    override val rootLayout: CoordinatorLayout by lazy { owm_forecast_CL }

    private lateinit var pagerAdapter: OWMForecastActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owm_forecast)

        toolbar.title = getString(R.string.forecast)

        setSupportActionBar(toolbar)

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

    override fun changeTitle(title: String) {
        toolbar.title = title
    }

    private fun initPager() {
        pagerAdapter = OWMForecastActivityPagerAdapter(supportFragmentManager)
        owm_forecast_pager.adapter = pagerAdapter
        owm_forecast_pager.visibility = GONE
    }

}
