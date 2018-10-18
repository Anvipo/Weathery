package ru.mts.avpopo85.weathery.presentation.base.withProgressBar

import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_progress_bar.*
import ru.mts.avpopo85.weathery.presentation.base.AbsBaseActivity

abstract class AbsProgressBarActivity : AbsBaseActivity(), BaseProgressBarContract.View {

    protected open val progressBarLayout: ViewGroup by lazy { layout_progress_bar }

    override fun showLoadingProgress() {
        progressBarLayout.visibility = View.VISIBLE
    }

    override fun hideLoadingProgress() {
        progressBarLayout.visibility = View.GONE
    }

}
