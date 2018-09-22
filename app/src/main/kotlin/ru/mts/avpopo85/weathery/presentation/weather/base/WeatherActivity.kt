package ru.mts.avpopo85.weathery.presentation.weather.base

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import ru.mts.avpopo85.weathery.presentation.base.BaseActivity

abstract class WeatherActivity/*<out P : WeatherContract.Presenter<WeatherContract.View>>*/ :
    BaseActivity/*<BaseContract.Presenter<BaseContract.View>>*/(),
    WeatherContract.View {

    protected abstract val progressBar: ProgressBar

    //    abstract override val presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //        presenter.onStart()
    }

    override fun showLoadingProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

}
