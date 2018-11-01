package ru.mts.avpopo85.weathery.presentation.weather.base.view.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.fragment.withProgressBar.withSwipeToRefresh.AbsSwipeToRefreshFragment
import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract

abstract class AbsWeatherFragment : AbsSwipeToRefreshFragment(), WeatherContract.View {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initInjection()

        initBindings()
    }

    final override fun onNotFreshWeatherData() {
        val part1 = getString(R.string.the_forecast_is_outdated)
        val part2 = getString(R.string.internet_connection_required)

        val message = "$part1. $part2"

        showLongSnackbar(message)
    }

    override fun onDestroyView() {
        unbindPresenter()
        super.onDestroyView()
    }

    final override fun changeTitle(title: String) {
        viewToolbar.title = title
    }

    final override fun hideLayout() {
        hidingLayout.visibility = View.GONE
    }

    final override fun showLayout() {
        hidingLayout.visibility = View.VISIBLE
    }

    protected open fun initBindings() {
        initSwipeRefreshLayout()

        bindPresenter()
    }

    protected abstract fun bindPresenter()

    protected abstract fun unbindPresenter()

    protected abstract fun initInjection()

    protected abstract val viewToolbar: ActionBar

    protected abstract val toolbarTitle: String

    protected abstract val hidingLayout: View

    private fun initView() {
        viewToolbar.title = toolbarTitle
    }

}