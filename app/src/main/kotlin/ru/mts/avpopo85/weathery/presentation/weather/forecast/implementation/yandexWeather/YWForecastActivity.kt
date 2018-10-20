package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import android.os.Bundle
import android.view.View.GONE
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_yw_forecast.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_yw_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWForecastModule
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters.YWForecastActivityPagerAdapter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastType
import javax.inject.Inject

class YWForecastActivity : AbsForecastActivity<YWForecastType>() {

    @Inject
    lateinit var presenter: ForecastContract.Presenter<YWForecastType>

    override val view: ViewPager by lazy { yw_forecast_pager }

    override val rootLayout: CoordinatorLayout by lazy { yw_forecast_CL }

    override val clickListener: (YWForecastType) -> Unit by lazy {
        TODO("not implemented")
    }

    private lateinit var pagerAdapter: YWForecastActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yw_forecast)

        toolbar.title = getString(R.string.forecast)

        setSupportActionBar(toolbar)

        App.appComponent
            .plus(YWForecastModule(this))
            .inject(this)

        initPager()

        presenter.onBindView(this)
        presenter.loadForecast()
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
    }

    override fun showWeatherResponse(data: YWForecastListType) {
        if (data.isNotEmpty()) {
            showLayout()
            fillRecyclerView(data)
        } else
            hideLayout()
    }

    override fun fillRecyclerView(data: YWForecastListType) {
        for (forecast in data) {
            val yfr = YWForecastFragment()

            yfr.arguments = Bundle().apply {
                putParcelable(ARG_FORECAST, forecast)
            }

            pagerAdapter.addFragment(yfr, forecast.date)
        }
    }

    override fun changeTitle(title: String) {
        toolbar.title = title
    }

    override fun initRecyclerView() {
        TODO("not implemented")
    }

    override fun startWeatherInfoActivity(itemData: YWForecastType) {
        TODO("not implemented")
    }

    private fun initPager() {
        pagerAdapter = YWForecastActivityPagerAdapter(supportFragmentManager)
        yw_forecast_pager.adapter = pagerAdapter
        yw_forecast_pager.visibility = GONE
    }

}
