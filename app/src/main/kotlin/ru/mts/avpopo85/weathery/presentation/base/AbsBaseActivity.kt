package ru.mts.avpopo85.weathery.presentation.base

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.base.utils.SnackbarLengths
import ru.mts.avpopo85.weathery.presentation.base.utils.SnackbarLengths.*
import ru.mts.avpopo85.weathery.presentation.base.utils.startActivity
import ru.mts.avpopo85.weathery.presentation.base.utils.startActivityForResult
import ru.mts.avpopo85.weathery.presentation.utils.onHttpException
import ru.mts.avpopo85.weathery.utils.common.GoogleGeocodeException
import ru.mts.avpopo85.weathery.utils.common.sendErrorLog
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class AbsBaseActivity : AppCompatActivity(), BaseContract.View {

    final override fun showError(message: String, isCritical: Boolean) {
        val length = if (!isCritical) nonCriticalSnackbarLength else criticalSnackbarLength

        showSnackbar(message, length)
    }

    final override fun showSnackbar(message: String, length: SnackbarLengths, rootView: View?) {
        val view = rootView ?: rootLayout

        when (length) {
            LENGTH_INDEFINITE -> view.indefiniteSnackbar(message)
            LENGTH_SHORT -> view.shortSnackbar(message)
            LENGTH_LONG -> view.longSnackbar(message)
        }
    }

    final override fun showAlertDialog(
        message: String,
        positiveButtonText: String,
        negativeButtonText: String,
        onClickedPositiveButton: () -> Unit,
        onClickedNegativeButton: () -> Unit,
        title: String?
    ) {
        AlertDialog.Builder(this, R.style.MyDialog).apply {
            if (title != null)
                setTitle(title)

            setMessage(message)

            setNegativeButton(negativeButtonText) { _, _ -> onClickedNegativeButton() }

            setPositiveButton(positiveButtonText) { _, _ -> onClickedPositiveButton() }
        }.create().show()
    }

    final override fun showError(error: Throwable, isCritical: Boolean) {
        error.printStackTrace()

        val message = parseError(error)

        sendErrorLog(message)

        showError(message)
    }

    protected inline fun <reified T : Activity> startActivity(vararg params: Pair<String, Any?>) {
        startActivity(this, T::class.java, params)
    }

    protected inline fun <reified T : Activity> startActivityForResult(
        requestCode: Int,
        vararg params: Pair<String, Any?>
    ) {
        startActivityForResult(this, T::class.java, requestCode, params)
    }

    protected abstract val rootLayout: View

    private fun getErrorMessageOrDefault(error: Throwable): String =
        error.localizedMessage ?: error.message ?: getString(R.string.unknown_error)

    private fun View.shortSnackbar(message: CharSequence) = Snackbar
        .make(this, message, Snackbar.LENGTH_SHORT)
        .apply { show() }

    private fun View.longSnackbar(message: CharSequence) = Snackbar
        .make(this, message, Snackbar.LENGTH_LONG)
        .apply { show() }

    private fun View.indefiniteSnackbar(message: CharSequence) = Snackbar
        .make(this, message, Snackbar.LENGTH_INDEFINITE)
        .apply { show() }

    private fun parseError(error: Throwable): String =
        when (error) {
            is GoogleGeocodeException -> onGoogleGeocodeException()
            is TimeoutException -> onTimeoutException()
            is HttpException -> onHttpException(error)
            is UnknownHostException -> onUnknownHostException()
            is ConnectException -> onConnectException()
            is SocketTimeoutException -> onSocketTimeoutException()
            else -> getErrorMessageOrDefault(error)
        }

    private fun onGoogleGeocodeException(): String =
        getString(R.string.could_not_find_address_of_your_current_location)

    private fun onTimeoutException(): String {
        val part1 = getString(R.string.request_timeout)
        val part2 = getString(R.string.please_try_later)

        return "$part1. $part2"
    }

    private fun onUnknownHostException(): String =
        "${getString(R.string.the_server_is_temporarily_unavailable)}. " +
                getString(R.string.please_try_later)

    private fun onConnectException(): String = getString(R.string.authorisation_error)

    private fun onSocketTimeoutException(): String =
        "${getString(R.string.response_timeout)}. " +
                getString(R.string.make_sure_your_device_has_a_network_connection)

    private val criticalSnackbarLength = LENGTH_INDEFINITE

    private val nonCriticalSnackbarLength = LENGTH_LONG

}
