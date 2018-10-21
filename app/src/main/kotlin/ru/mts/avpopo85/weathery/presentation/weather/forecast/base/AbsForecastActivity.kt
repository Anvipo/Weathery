package ru.mts.avpopo85.weathery.presentation.weather.forecast.base

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter

abstract class AbsForecastActivity<T : IForecast> :
    AbsWeatherActivity(),
    ForecastContract.View<T> {

    abstract val clickListener: (T) -> Unit

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
    }

    final override fun showWeatherResponse(data: List<T>) {
        if (data.isNotEmpty()) {
            showLayout()
            fillRecyclerView(data)
        } else
            hideLayout()
    }

    final override fun bindPresenter() {
        presenter.onBindView(this)
    }

    final override fun unbindPresenter() {
        presenter.onUnbindView()
    }

    final override fun onRefresh() {
        presenter.onSwipeToRefresh()
    }

    final override fun initBindings() {
        super.initBindings()

        presenter.loadWeatherData()
    }

    final override val toolbarTitle: String by lazy { getString(R.string.forecast) }

    protected abstract val presenter: ForecastContract.Presenter<T>

    protected abstract val mAdapter: IForecastAdapter<T>

    protected abstract val recyclerViewId: Int

    private fun fillRecyclerView(data: List<T>) {
        mAdapter.addAll(data)
    }

    private fun initRecyclerView() {
        findViewById<RecyclerView>(recyclerViewId)?.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(this@AbsForecastActivity)

            adapter = mAdapter as RecyclerView.Adapter<*>
        }
    }

}
