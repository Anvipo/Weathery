package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.view.fragment

import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.base.view.fragment.AbsWeatherFragment
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract

abstract class AbsCurrentWeatherFragment<T : ICurrentWeather> :
    AbsWeatherFragment(),
    CurrentWeatherContract.View<T> {

    override fun showWeatherResponse(data: T) {
        showLayout()
    }

    final override val toolbarTitle: String by lazy { getString(R.string.current_weather) }

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

    final override fun onNewLocation() {
        if (!this::presenter.isInitialized) {
            //TODO
            initInjection()
        }

        presenter.onNewLocation()
    }

    protected open lateinit var presenter: CurrentWeatherContract.Presenter<T>

}