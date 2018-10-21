package ru.mts.avpopo85.weathery.presentation.base.withProgressBar.withSwipeToRefresh

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.AbsProgressBarActivity

abstract class AbsSwipeToRefreshActivity :
    AbsProgressBarActivity(),
    BaseSwipeToRefreshContract.View,
    SwipeRefreshLayout.OnRefreshListener {

    protected abstract val swipeRefreshLayout: SwipeRefreshLayout

    override fun hideRefreshingIndicator() {
        swipeRefreshLayout.isRefreshing = false
    }

    protected abstract fun setOnRefreshListener()

    protected open fun setColorSchemeResources() {
        swipeRefreshLayout.setColorSchemeResources(R.color.accentColor)
    }

}
