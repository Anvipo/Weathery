package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import android.os.Bundle
import android.view.View.GONE
import android.widget.ProgressBar
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_yw_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.yandexWeather.YWForecastModule
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters.YWForecastActivityPagerAdapter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType
import javax.inject.Inject

class YWForecastActivity :
    AbsForecastActivity<ViewPager, YWForecastListType>(),
    ForecastContract.View<YWForecastListType> {

    @Inject
    lateinit var presenter: ForecastContract.Presenter<YWForecastListType>

    override val progressBar: ProgressBar by lazy { yw_forecast_PB }

    override val view: ViewPager by lazy { yw_forecast_pager }

    private lateinit var pagerAdapter: YWForecastActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yw_forecast)

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
            putForecastDataInPager(data)
        } else
            hideLayout()
    }

    override fun putForecastDataInPager(data: YWForecastListType) {
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

    private fun initPager() {
        pagerAdapter = YWForecastActivityPagerAdapter(supportFragmentManager)
        yw_forecast_pager.adapter = pagerAdapter
        yw_forecast_pager.visibility = GONE
    }

}
