package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.view.activity

import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.base.view.activity.AbsWeatherActivity
import ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base.CurrentWeatherContract

abstract class AbsCurrentWeatherActivity<T : ICurrentWeather> :
    AbsWeatherActivity(),
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

    protected abstract val presenter: CurrentWeatherContract.Presenter<T>

}
