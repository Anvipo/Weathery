package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap

import android.os.Bundle
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_owm_forecast.*
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.OpenWeatherMapModule
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.utils.openWeatherMap.OWMForecastListType
import javax.inject.Inject

class OWMForecastActivity : AbsForecastActivity(),
    ForecastContract.View<OWMForecastListType> {

    @Inject
    lateinit var forecastPresenter: ForecastContract.Presenter<OWMForecastListType>

    override val progressBar: ProgressBar by lazy { owm_forecast_PB }

//    private lateinit var pagerAdapter: ForecastActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owm_forecast)

        App.appComponentForYandexWeather.plus(OpenWeatherMapModule())
            .inject(this)

//        initPager()

        forecastPresenter.onBindView(this)
        forecastPresenter.loadForecast()
    }

    override fun onDestroy() {
        forecastPresenter.onUnbindView()
        super.onDestroy()
    }

    override fun showWeatherResponse(data: OWMForecastListType) {
        val c = 1
//        if (data.isNotEmpty()) {
//            pager.visibility = VISIBLE
//            putForecastDataInPager(data)
//        } else
//            pager.visibility = GONE
    }

    /*private fun putForecastDataInPager(data: YWForecastListType) {
        for (forecast in data) {
            val yfr = YWForecastFragment()

            yfr.arguments = Bundle().apply {
                putParcelable(ARG_FORECAST, forecast)
            }

            val title = makeTitle(forecast)

            pagerAdapter.addFragment(yfr, title)
        }
    }*/

    /*private fun initPager() {
        pagerAdapter = ForecastActivityPagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter
        pager.visibility = GONE
    }*/

}
