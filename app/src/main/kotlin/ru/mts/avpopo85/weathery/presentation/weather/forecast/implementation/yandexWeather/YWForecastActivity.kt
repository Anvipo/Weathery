package ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_yandex_forecast.pager
import kotlinx.android.synthetic.main.activity_yandex_forecast.yandex_forecast_PB
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.application.App
import ru.mts.avpopo85.weathery.di.modules.YandexWeatherModule
import ru.mts.avpopo85.weathery.presentation.utils.ARG_FORECAST
import ru.mts.avpopo85.weathery.presentation.utils.makeTitle
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.AbsForecastActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.yandexWeather.adapters.ForecastActivityPagerAdapter
import ru.mts.avpopo85.weathery.utils.yandexWeather.YWForecastListType
import javax.inject.Inject

class YWForecastActivity : AbsForecastActivity(),
    ForecastContract.View<YWForecastListType> {

    @Inject
    lateinit var forecastPresenter: ForecastContract.Presenter<YWForecastListType>

    override val progressBar: ProgressBar by lazy { yandex_forecast_PB }

    private lateinit var pagerAdapter: ForecastActivityPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yandex_forecast)

        App.appComponentForYandexWeather.plus(YandexWeatherModule())
            .inject(this)

        initPager()

        forecastPresenter.onBindView(this)
        forecastPresenter.loadForecast()
    }

    override fun onDestroy() {
        forecastPresenter.onUnbindView()
        super.onDestroy()
    }

    override fun showWeatherResponse(data: YWForecastListType) {
        if (data.isNotEmpty()) {
            pager.visibility = VISIBLE
            putForecastDataInPager(data)
        } else
            pager.visibility = GONE
    }

    private fun putForecastDataInPager(data: YWForecastListType) {
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
        pagerAdapter = ForecastActivityPagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter
        pager.visibility = GONE
    }

}
