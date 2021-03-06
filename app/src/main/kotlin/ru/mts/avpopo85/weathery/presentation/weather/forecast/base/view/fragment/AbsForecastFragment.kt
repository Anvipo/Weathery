package ru.mts.avpopo85.weathery.presentation.weather.forecast.base.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.common.IForecast
import ru.mts.avpopo85.weathery.presentation.weather.base.view.fragment.AbsWeatherFragment
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastNotificationHelper
import ru.mts.avpopo85.weathery.presentation.weather.forecast.implementation.openWeatherMap.adapter.base.IForecastAdapter
import kotlin.concurrent.thread


abstract class AbsForecastFragment<T : IForecast> :
    AbsWeatherFragment(),
    ForecastContract.View<T> {

    abstract val itemClickListener: (T) -> Unit

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.init()
    }

    @ExperimentalUnsignedTypes
    final override fun showWeatherResponse(forecast: List<T>) {
        if (forecast.isNotEmpty()) {
            updateRecyclerViewData(data = forecast)
            showLayout()
            thread(start = true) {
                ForecastNotificationHelper.checkPrecipitations(
                    forecast = forecast,
                    context = context!!
                )
            }
        } else
            hideLayout()
    }

    final override fun bindPresenter() {
        presenter.onBindView(view = this)
    }

    final override fun unbindPresenter() {
        presenter.onUnbindView()
    }

    final override fun onRefresh() {
        presenter.onSwipeToRefresh()
    }

    final override fun onStart() {
        super.onStart()

        presenter.loadWeatherData()
    }

    final override fun onStop() {
        super.onStop()

        presenter.clearCompositeDisposable()
        hideLayout()
    }

    final override val toolbarTitle: String by lazy { getString(R.string.forecast) }

    protected open lateinit var presenter: ForecastContract.Presenter<T>

    protected abstract val adapter: IForecastAdapter<T>

    protected abstract val recyclerView: RecyclerView

    private fun updateRecyclerViewData(data: List<T>) {
        adapter.updateData(newData = data)
    }

    private fun RecyclerView.init() {
        apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(this@AbsForecastFragment.context!!)

            adapter = this@AbsForecastFragment.adapter as RecyclerView.Adapter<*>
        }
    }

}
