package ru.mts.avpopo85.weathery.presentation.base.fragment

import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.exceptions.CompositeException
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.repository.weather.utils.PreviousLocationUnknownException
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

    override fun showError(message: String, isCritical: Boolean, rootView: View?) {
        if (!isCritical) {
            showLongSnackbar(message, rootView)
        } else {
            showIndefiniteSnackbar(message, rootView)
        }
    }

    @Suppress("LocalVariableName")
    final override fun showError(error: Throwable, isCritical: Boolean, rootView: View?) {
        error.printStackTrace()

        val cause = if (error is CompositeException) {
            error.exceptions.last()!!
        } else {
            error
        }

        sendErrorLog(cause.toString())

        val message = context!!.parseError(cause)

        sendErrorLog(message)

        val internetConnectionRequired =
            if (cause is MyRealmException.DBHasNoWeatherResponseException) {
                !cause.isConnectedToInternet
            } else {
                false
            }

        val _isCritical =
            cause is PreviousLocationUnknownException || isCritical || internetConnectionRequired

        showError(message, _isCritical,  rootView)
    }

    protected abstract val rootLayout: View

}