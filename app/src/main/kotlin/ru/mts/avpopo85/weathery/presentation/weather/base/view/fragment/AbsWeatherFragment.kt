package ru.mts.avpopo85.weathery.presentation.weather.base.view.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.fragment.withProgressBar.withSwipeToRefresh.AbsSwipeToRefreshFragment
import ru.mts.avpopo85.weathery.presentation.weather.base.WeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.tab.TabbedWeatherActivity

abstract class AbsWeatherFragment : AbsSwipeToRefreshFragment(), WeatherContract.View {

    final override fun onNotFreshWeatherData() {
        val part1 = getString(R.string.the_forecast_is_outdated)
        val part2 = getString(R.string.internet_connection_required)

        val message = "$part1. $part2"

        showLongSnackbar(message)
    }

    //snackbar has fast dismiss, so here snackbar is overriden
    final override fun showError(
        message: String,
        isCritical: Boolean,
        rootView: View?
    ) {
        if (!isCritical && !TabbedWeatherActivity.snackbarIsShownOrQueued) {
            TabbedWeatherActivity.snackbarIsShownOrQueued = true
            longSnackbar.apply { setText(message) }.show()
        } else if (!TabbedWeatherActivity.snackbarIsShownOrQueued) {
            TabbedWeatherActivity.snackbarIsShownOrQueued = true
            indefiniteSnackbar.apply { setText(message) }.show()
        }
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

    //if final, then run-time crush
    override fun onDestroyView() {
        unbindPresenter()
        super.onDestroyView()
    }

    final override fun onResume() {
        super.onResume()

        dismissSnackbar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initInjection()

        initBindings()
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

    protected fun dismissSnackbar() {
        longSnackbar.dismiss()
        indefiniteSnackbar.dismiss()
    }

    private fun initView() {
        viewToolbar.title = toolbarTitle
    }

    //if xml will be edited, then it must be edited also
    private val tabbedWeather: CoordinatorLayout by lazy { rootLayout.parent.parent as CoordinatorLayout }

    private val snackbarCallback = object : Snackbar.Callback() {

        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            TabbedWeatherActivity.snackbarIsShownOrQueued = false
        }

    }

    private val longSnackbar: Snackbar by lazy {
        Snackbar.make(tabbedWeather, "", Snackbar.LENGTH_LONG)
            .apply { addCallback(snackbarCallback) }
    }

    private val indefiniteSnackbar: Snackbar by lazy {
        Snackbar.make(tabbedWeather, "", Snackbar.LENGTH_INDEFINITE)
            .apply { addCallback(snackbarCallback) }
    }

}