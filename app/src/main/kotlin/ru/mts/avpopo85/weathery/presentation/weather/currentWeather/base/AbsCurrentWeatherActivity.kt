package ru.mts.avpopo85.weathery.presentation.weather.currentWeather.base

import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.domain.model.base.common.ICurrentWeather
import ru.mts.avpopo85.weathery.presentation.weather.base.AbsWeatherActivity

abstract class AbsCurrentWeatherActivity<T : ICurrentWeather> :
    AbsWeatherActivity(),
    CurrentWeatherContract.View<T> {

    protected abstract val presenter: CurrentWeatherContract.Presenter<T>

    override val toolbarTitle: String by lazy { getString(R.string.current_weather) }

    override fun bindPresenter() {
        presenter.onBindView(this)
    }

    override fun unbindPresenter() {
        presenter.onUnbindView()
    }

    override fun onRefresh() {
        presenter.onSwipeToRefresh()
    }

    override fun initBindings() {
        super.initBindings()

        presenter.loadWeatherData()
    }

    override fun showWeatherResponse(data: T) {
        showLayout()
    }

}
