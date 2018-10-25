package ru.mts.avpopo85.weathery.presentation.weather.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.withSwipeToRefresh.AbsSwipeToRefreshActivity

abstract class AbsWeatherActivity : AbsSwipeToRefreshActivity(), WeatherContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        initInjection()

        initBindings()
    }

    final override fun hideLayout() {
        swipeRefreshLayout.visibility = View.GONE
    }

    final override fun showLayout() {
        swipeRefreshLayout.visibility = View.VISIBLE
    }

    final override fun onNotFreshWeatherData() {
        val part1 = getString(R.string.the_forecast_is_outdated)
        val part2 = getString(R.string.internet_connection_required)

        val message = "$part1. $part2"

        showLongSnackbar(message)
    }

    final override fun onDestroy() {
        unbindPresenter()
        super.onDestroy()
    }

    final override fun changeTitle(title: String) {
        viewToolbar.title = title
    }

    protected open fun initBindings() {
        initSwipeRefreshLayout()

        bindPresenter()
    }

    protected abstract fun bindPresenter()

    protected abstract fun unbindPresenter()

    protected abstract fun initInjection()

    protected abstract val layoutResID: Int

    protected abstract val viewToolbar: Toolbar

    protected abstract val toolbarTitle: String

    protected open fun initView() {
        setContentView(layoutResID)

        viewToolbar.title = toolbarTitle

        setSupportActionBar(viewToolbar)
    }

}
