package ru.mts.avpopo85.weathery.presentation.base

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import retrofit2.HttpException
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.presentation.utils.*
import ru.mts.avpopo85.weathery.utils.common.GoogleGeocodeException
import ru.mts.avpopo85.weathery.utils.common.sendErrorLog
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

//TODO как это реализовать, чтобы прокинуть это всё к наследникам?
abstract class AbsBaseActivity/*<out P : BaseContract.Presenter<BaseContract.View>>*/ :
    AppCompatActivity(),
    BaseContract.View {

    /*protected abstract val presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.onBindView(this)
    }

    override fun onDestroy() {
        presenter.onUnbindView()
        super.onDestroy()
    }*/

    override fun getErrorMessageOrDefault(error: Throwable): String =
        error.localizedMessage ?: error.message ?: getString(R.string.unknown_error)

    override fun showError(message: String) {
        longToast(message)
    }

    override fun showToast(message: String) {
        toast(message)
    }

    override fun showLongToast(message: String) {
        longToast(message)
    }

    override fun showIndefiniteSnackbar(message: String?, view: View?) {
        if (message != null && view != null) {
            Snackbar
                .make(view, message, Snackbar.LENGTH_INDEFINITE)
                .apply { show() }
        }
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

        longToast(message)
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

    private fun onHttpException(error: HttpException): String {
        val code = error.code()

        val res =
            when (code) {
                in 100..199 -> parse1xxHttpCode(code)
                in 200..299 -> parse2xxHttpCode(code)
                in 300..399 -> parse3xxHttpCode(code)
                in 400..499 -> parse4xxHttpCode(code)
                in 500..599 -> parse5xxHttpCode(code)
                else -> "${getString(R.string.unknown_http_code)}. ${getString(R.string.please_try_later)}"
            }

        val url =
            if (code in 400..499 && code != 403)
                " (${error.response().raw().request().url()})"
            else ""

        return "$res$url"
    }

    private fun onUnknownHostException(): String =
        "${getString(R.string.the_server_is_temporarily_unavailable)}. " +
                getString(R.string.please_try_later)

    private fun onConnectException(): String = getString(R.string.authorisation_error)

    private fun onSocketTimeoutException(): String =
        "${getString(R.string.response_timeout)}. " +
                getString(R.string.make_sure_your_device_has_a_network_connection)

}
