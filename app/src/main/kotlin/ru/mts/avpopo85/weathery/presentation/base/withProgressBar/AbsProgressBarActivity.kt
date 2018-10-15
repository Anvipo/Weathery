package ru.mts.avpopo85.weathery.presentation.base.withProgressBar

import android.view.View
import android.widget.ProgressBar
import ru.mts.avpopo85.weathery.presentation.base.AbsBaseActivity

abstract class AbsProgressBarActivity : AbsBaseActivity(), HasProgressBar {

    protected abstract val progressBar: ProgressBar

    override fun showLoadingProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        progressBar.visibility = View.GONE
    }

    override fun isLoadingProgressShown(): Boolean = progressBar.isShown

}
