package ru.mts.avpopo85.weathery.presentation.weather.base

import android.view.View
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.withProgressBar.AbsProgressBarActivity

abstract class AbsWeatherActivity<V : View>/*<out P : WeatherContract.Presenter<WeatherContract.View>>*/ :
    AbsProgressBarActivity/*<BaseContract.Presenter<BaseContract.View>>*/(),
    WeatherContract.View {

    protected abstract val view: V

    /*abstract override val presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.onStart()
    }*/

    override fun hideLayout() {
        view.visibility = View.GONE
    }

    override fun showLayout() {
        view.visibility = View.VISIBLE
    }

    override fun onNotFreshWeatherData() {
        val part1 = getString(R.string.the_forecast_is_outdated)
        val part2 = getString(R.string.internet_connection_required)

        val message = "$part1. $part2"

        showLongToast(message)
    }

}
