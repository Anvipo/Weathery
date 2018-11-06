package ru.mts.avpopo85.weathery.presentation.base.activity

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.exceptions.CompositeException
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.data.repository.weather.utils.PreviousLocationUnknownException
import ru.mts.avpopo85.weathery.presentation.base.common.BaseContract
import ru.mts.avpopo85.weathery.presentation.base.utils.startActivity
import ru.mts.avpopo85.weathery.presentation.base.utils.startActivityForResult
import ru.mts.avpopo85.weathery.utils.common.*
import java.net.SocketTimeoutException

abstract class AbsBaseActivity : AppCompatActivity(), BaseContract.View {

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

        val cause = if (error is CompositeException) {
            error.exceptions.last()!!
        } else {
            error
        }

        sendErrorLog(cause.toString())

        val message = parseError(cause)

        sendErrorLog(message)

        val internetConnectionRequired =
            if (cause is MyRealmException.DBHasNoWeatherResponseException) {
                !cause.isConnectedToInternet
            } else {
                false
            }

        @Suppress("LocalVariableName")
        val _isCritical =
            cause is PreviousLocationUnknownException || isCritical || internetConnectionRequired

        showError(message, _isCritical, rootView)
    }

    final override fun notifyAbout(error: Throwable) {
        val message: String = if (error is SocketTimeoutException) {
            "Connection error occurs. Retrying"
        } else {
            getErrorMessageOrDefault(error)
        }

        showLongSnackbar(message)
    }

    protected inline fun <reified T : Activity> Activity.startActivity(vararg params: Pair<String, Any?>) {
        startActivity(this, T::class.java, params)
    }

    protected inline fun <reified T : Activity> startActivityForResult(
        requestCode: Int,
        vararg params: Pair<String, Any?>
    ) {
        startActivityForResult(this, T::class.java, requestCode, params)
    }

    protected abstract val rootLayout: View

}
