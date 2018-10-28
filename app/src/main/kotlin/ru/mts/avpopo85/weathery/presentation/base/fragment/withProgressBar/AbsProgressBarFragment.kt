package ru.mts.avpopo85.weathery.presentation.base.fragment.withProgressBar

import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_progress_bar.*
import ru.mts.avpopo85.weathery.presentation.base.common.withProgressBar.BaseProgressBarContract
import ru.mts.avpopo85.weathery.presentation.base.fragment.AbsBaseFragment

abstract class AbsProgressBarFragment : AbsBaseFragment(), BaseProgressBarContract.View {

    final override fun showLoadingProgress() {
        progressBarLayout.visibility = View.VISIBLE
    }

    final override fun hideLoadingProgress() {
        progressBarLayout.visibility = View.GONE
    }

    protected open val progressBarLayout: ViewGroup by lazy { layout_progress_bar }

}