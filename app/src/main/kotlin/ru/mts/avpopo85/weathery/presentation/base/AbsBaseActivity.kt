package ru.mts.avpopo85.weathery.presentation.base

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import ru.mts.avpopo85.weathery.R
import ru.mts.avpopo85.weathery.utils.common.sendErrorLog

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

    override fun showError(throwable: Throwable) {
        throwable.printStackTrace()

        val message =
            throwable.localizedMessage ?: throwable.message ?: getString(R.string.unknown_error)

        sendErrorLog(message)

        longToast(message)
    }

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

}
