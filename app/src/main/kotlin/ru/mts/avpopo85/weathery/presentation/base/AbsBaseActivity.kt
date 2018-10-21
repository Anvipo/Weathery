package ru.mts.avpopo85.weathery.presentation.base

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar
import retrofit2.HttpException
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.utils.onHttpException
import ru.mts.avpopo85.weathery.utils.common.GoogleGeocodeException
import ru.mts.avpopo85.weathery.utils.common.sendErrorLog
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class AbsBaseActivity : AppCompatActivity(), BaseContract.View {

    protected abstract val rootLayout: View

    protected fun getErrorMessageOrDefault(error: Throwable): String =
        error.localizedMessage ?: error.message ?: getString(R.string.unknown_error)

    override fun showError(message: String) {
        showLongSnackbar(message)
    }

    override fun showShortSnackbar(message: String, view: View?) {
        val _view = view ?: rootLayout
        _view.snackbar(message)
    }

    override fun showLongSnackbar(message: String, view: View?) {
        val _view = view ?: rootLayout
        _view.longSnackbar(message)
    }

    override fun showIndefiniteSnackbar(message: String, view: View?) {
        val _view = view ?: rootLayout
        _view.indefiniteSnackbar(message)
    }

    override fun showAlertDialog(
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

    override fun showError(error: Throwable) {
        error.printStackTrace()

        val message = parseError(error)

        sendErrorLog(message)

        showLongSnackbar(message)
    }

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

}
