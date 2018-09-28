package ru.mts.avpopo85.weathery.presentation.weather.base

import android.view.View
import android.widget.ProgressBar
import ru.mts.avpopo85.weathery.presentation.base.AbsBaseActivity

abstract class AbsWeatherActivity<V : View>/*<out P : WeatherContract.Presenter<WeatherContract.View>>*/ :
    AbsBaseActivity/*<BaseContract.Presenter<BaseContract.View>>*/(),
    WeatherContract.View {

    protected abstract val progressBar: ProgressBar

    protected abstract val view: V

    /*abstract override val presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.onStart()
    }*/

    override fun showLoadingProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

    override fun hideLayout() {
        view.visibility = View.GONE
    }

    override fun showLayout() {
        view.visibility = View.VISIBLE
    }

}
