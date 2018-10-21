package ru.mts.avpopo85.weathery.presentation.weather.base

import android.os.Bundle
import android.view.View
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.withSwipeToRefresh.AbsSwipeToRefreshActivity

abstract class AbsWeatherActivity : AbsSwipeToRefreshActivity(), WeatherContract.View {

    override fun hideLayout() {
        swipeRefreshLayout.visibility = View.GONE
    }

    override fun showLayout() {
        swipeRefreshLayout.visibility = View.VISIBLE
    }

    override fun onNotFreshWeatherData() {
        val part1 = getString(R.string.the_forecast_is_outdated)
        val part2 = getString(R.string.internet_connection_required)

        val message = "$part1. $part2"

        showLongSnackbar(message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        initInjection()

        initBindings()
    }

    override fun onDestroy() {
        unbindPresenter()
        super.onDestroy()
    }

    protected abstract fun initBindings()

    protected abstract fun bindPresenter()

    protected abstract fun unbindPresenter()

    protected abstract fun initInjection()

    protected abstract fun initView()

}
