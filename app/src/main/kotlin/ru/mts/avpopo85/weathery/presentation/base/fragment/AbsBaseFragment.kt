package ru.mts.avpopo85.weathery.presentation.base.fragment

import android.view.View
import androidx.fragment.app.Fragment
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.common.BaseContract
import ru.mts.avpopo85.weathery.utils.common.*

abstract class AbsBaseFragment : Fragment(), BaseContract.View {

    final override fun onUnexpectedApplicationBehavior(rootView: View?) {
        val message = getString(R.string.unexpected_application_behavior)

        showIndefiniteSnackbar(message)
    }

    final override fun showShortSnackbar(message: String, rootView: View?) {
        val view = rootView ?: rootLayout

        view.showShortSnackbar(message)
    }

    final override fun showLongSnackbar(message: String, rootView: View?) {
        val view = rootView ?: rootLayout

        view.showLongSnackbar(message)
    }

    final override fun showIndefiniteSnackbar(message: String, rootView: View?) {
        val view = rootView ?: rootLayout

        view.showIndefiniteSnackbar(message)
    }

    final override fun showError(message: String, isCritical: Boolean, rootView: View?) {
        if (!isCritical) {
            showLongSnackbar(message, rootView)
        } else {
            showIndefiniteSnackbar(message, rootView)
        }
    }

    final override fun showError(error: Throwable, isCritical: Boolean, rootView: View?) {
        error.printStackTrace()

        context?.let {
            val message = it.parseError(error)

            sendErrorLog(message)

            showError(message, isCritical, rootView)
        }
    }

    protected abstract val rootLayout: View

}