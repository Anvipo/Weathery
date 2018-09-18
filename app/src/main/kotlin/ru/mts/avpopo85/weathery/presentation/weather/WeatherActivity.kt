package ru.mts.avpopo85.weathery.presentation.weather

import android.view.View
import android.widget.ProgressBar
import ru.mts.avpopo85.weathery.presentation.base.BaseActivity

abstract class WeatherActivity : BaseActivity(), WeatherContract.View {

    protected abstract val progressBar: ProgressBar

    override fun showLoadingProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

}
