package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.view.fragment

import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.base.view.fragment.AbsWeatherFragment
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract
import ru.mts.avpopo85.weathery.presentation.weather.forecast.base.ForecastNotificationHelper
import kotlin.concurrent.thread

abstract class AbsCurrentWeatherFragment<T : ICurrentWeather> :
    AbsWeatherFragment(),
    CurrentWeatherContract.View<T> {

    override fun showWeatherResponse(currentWeather: T) {
        showLayout()
        thread(start = true) {
            ForecastNotificationHelper.checkCurrentWeatherPrecipitations(
                currentWeather = currentWeather,
                context = context!!
            )
        }
    }

    final override val toolbarTitle: String by lazy { getString(R.string.current_weather) }

    final override fun bindPresenter() {
        presenter.onBindView(this)
    }

    final override fun unbindPresenter() {
        presenter.onUnbindView()
    }

    final override fun onRefresh() {
        dismissSnackbar()
        presenter.onSwipeToRefresh()
    }

    final override fun onStart() {
        super.onStart()

        dismissSnackbar()
        presenter.loadWeatherData()
    }

    final override fun onStop() {
        super.onStop()

        presenter.clearCompositeDisposable()
        hideLayout()
    }

    protected open lateinit var presenter: CurrentWeatherContract.Presenter<T>

}